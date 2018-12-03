package com.reading.controller.library;

import com.alibaba.fastjson.JSON;
import com.reading.base.BaseLibraryController;
import com.reading.data.model.*;
import com.reading.data.service.*;
import com.reading.model.TuLingAdd;
import com.reading.model.TuLingAddSuccessData;
import com.reading.model.TuLingDell;
import com.reading.model.TuLingMatchingScore;
import com.reading.utils.*;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/3/23.
 */
@Controller
@RequestMapping("library/robot")
public class LibraryRobotController extends BaseLibraryController {
    @Resource
    private RobotConsultRecordService recordService;
    @Resource
    private YySeatAppointmentService appointmentService;
    @Resource
    WechatThreeAuthorizeService authorizeService;
    @Resource
    private RobotConfigService robotConfigService;
    @Resource
    private RobotCorpusService corpusService;
    private String table = "robot";

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 机器人问答记录
     *
     * @param request
     * @param p
     * @return
     */
    @RequestMapping("recordList")
    public String getRobotConsultRecord(HttpServletRequest request, Integer p) {
        p = p != null && p > 1 ? p : 1;
        String sql = "SELECT a.* from robot_consult_record a where a.id in (SELECT MAX(id) from  robot_consult_record where `status`=1 and library_id=" + getLibrary(request).getId() + " GROUP BY card_number ) limit " + (p - 1) * 20 + ",20";
        String sqlCount = "select count(a.id) from ( SELECT id FROM `robot_consult_record` where `status`=1 and library_id=" + getLibrary(request).getId() + " GROUP BY card_number ) a";
        List <RobotConsultRecord> list = recordService.selectBySql(sql);
        int i = (p - 1) * 20 + 1;
        for (RobotConsultRecord bean : list) {
            bean.setNumber(i++);
        }
        request.setAttribute("p", p);
        request.setAttribute("size", 20);
        request.setAttribute("count", appointmentService.getAppointmentSeatCount(sqlCount));
        request.setAttribute("list", list);
        return display(table + "_recordList");
    }

    /**
     * 获得当前用户的聊天记录详情
     *
     * @param request
     * @param id      当前数据的id
     * @return
     */
    @RequestMapping("getRobotDetails")
    public String getRobotDetails(HttpServletRequest request, Long id, Integer p) {
        p = p != null && p > 1 ? p : 1;
        RobotConsultRecord record = recordService.find(id);
        //获得聊天的历史记录
        RobotConsultRecordExample example = new RobotConsultRecordExample();
        example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId()).andCardNumberEqualTo(record.getCardNumber());
        example.setOrderByClause("id desc");
        example.setLimitStart((p - 1) * 20);
        example.setLimitEnd(20);
        List <RobotConsultRecord> list = recordService.selectByExample(example);
        list.sort((o1, o2) -> Integer.parseInt(String.valueOf(o1.getId() - o2.getId())));
        request.setAttribute("list", list);
        request.setAttribute("id", id);
        return display(table + "_robotdetails");
    }

    /**
     * 获得当前用户的聊天记录详情,分页
     *
     * @param request
     * @param id      当前数据的id
     * @return
     */
    @RequestMapping("getRobotDetailsByP")
    @ResponseBody
    public Object getRobotDetailsByP(HttpServletRequest request, Long id, Integer p) {
        p = p != null && p > 1 ? p : 1;
        RobotConsultRecord record = recordService.find(id);
        //获得聊天的历史记录
        RobotConsultRecordExample example = new RobotConsultRecordExample();
        example.setLimitStart((p - 1) * 20);
        example.setLimitEnd(20);
        example.createCriteria().andStatusEqualTo(1).andLibraryIdEqualTo(getLibrary(request).getId()).andCardNumberEqualTo(record.getCardNumber());
        example.setOrderByClause("id desc");
        List <RobotConsultRecord> list = recordService.selectByExample(example);

        list.forEach(robotConsultRecord -> {
            robotConsultRecord.setD1(dateFormat.format(robotConsultRecord.getCreateTime()));
        });

        return ResultUtil.success(list);
    }


//    ====================私有语料库的后台代码维护============================================================================
/**
 * 特此声明，为了防止修改不能立马生效的问题，将修改操作转化成先删除后新增操作
 *
 */


    /**
     * 设置语料库的匹配度
     *
     * @param request
     * @param matchingScore
     * @return
     */
    @RequestMapping("setMatchingScore")
    @ResponseBody
    public Object setMatchingScore(HttpServletRequest request, Integer matchingScore) {
        try {
            //获得本图书馆机器人的相关的参数
            RobotConfig config = getRobotConfig(request);
            if (config == null || config.getToken() == null||"".equals(config.getToken().trim())) {
                return ResultUtil.error("设置失败！请先购买专业版" );
            }

            String[] arr = getTuLingToken(config.getToken());
            TuLingMatchingScore score = new TuLingMatchingScore();
            score.setApikey(config.getApikey());
            score.setTimestamp(arr[1]);
            score.setToken(arr[0]);
            TuLingMatchingScore.DataBean data = new TuLingMatchingScore.DataBean();
            data.setMatch(matchingScore);
            score.setData(data);
            String df = HttpUtil.sendPost(TuLingUtil.matchingScore, JSON.toJSONString(score));
            JSONObject jsonObject = new JSONObject(df);
            int code = jsonObject.getInt("code");

            if (code != 0) {
                return ResultUtil.error("语料库匹配符仅支持输入10~100!");
            } else {
                /**
                 * code 0代表成功，成功后需要将数据插入本地的数据库
                 */
                config.setMatchingScore(matchingScore);
                robotConfigService.save(config);
                return ResultUtil.success(null, "操作成功！", "");
            }

        } catch (Exception e) {
            LogUtil.log("设置语料库匹配度发生错误");
            e.printStackTrace();
            return ResultUtil.error("设置失败，请重新操作！");
        }
    }

    public RobotConfig getRobotConfig(HttpServletRequest request) {
        RobotConfigExample example = new RobotConfigExample();
        example.createCriteria().andLibraryIdEqualTo(getLibrary(request).getId()).andStatusEqualTo(1);
        List <RobotConfig> list = robotConfigService.selectByExample(example);
        return list.stream().findFirst().orElse(null);
    }

    //图灵添加语料库
    public Boolean addTuLingCorpus(HttpServletRequest request, List <RobotCorpus> listCorpus) {
        try {
            RobotConfig config = getRobotConfig(request);
            if (listCorpus.size() == 0 || config == null || config.getToken() == null || "".equals(config.getToken().trim())) {

                return false;
            }
            TuLingAdd tuLingAdd = new TuLingAdd();
            tuLingAdd.setApikey(config.getApikey());
            String[] arr = getTuLingToken(config.getToken());
            tuLingAdd.setTimestamp(arr[1]);
            tuLingAdd.setToken(arr[0]);

            TuLingAdd.DataBean dataBean = new TuLingAdd.DataBean();

            List <TuLingAdd.DataBean.ListBean> list = new ArrayList();


            for (RobotCorpus robotCorpus : listCorpus) {
                TuLingAdd.DataBean.ListBean bean = new TuLingAdd.DataBean.ListBean();
                bean.setAnswer(robotCorpus.getAnswer());
                bean.setQuestion(robotCorpus.getQuestion());
                list.add(bean);
            }

            dataBean.setList(list);
            tuLingAdd.setData(dataBean);

            String df = HttpUtil.sendPost(TuLingUtil.addUrl, JSON.toJSONString(tuLingAdd));

            TuLingAddSuccessData data = JSON.parseObject(df, TuLingAddSuccessData.class);
            if (data.getCode() == 0) {
//TODO 添加后会返回主键需要存在库中
                List <TuLingAddSuccessData.DataBean.KnowledgeListBean> nowledgeList = data.getData().getKnowledgeList();

                for (int i = 0; i < nowledgeList.size(); i++) {
                    RobotCorpus robotCorpus = listCorpus.get(i);
                    robotCorpus.setTulingId(nowledgeList.get(i).getId());
                    corpusService.save(robotCorpus);
                }

            } else {
                LogUtil.log("权限或者网络错误！");
                return false;
            }
            return true;
        } catch (Exception e) {
            LogUtil.log("同步语料库错误！");
            e.printStackTrace();

            return false;
        }
    }

    //删除
    public Boolean delCorpus(HttpServletRequest request, List <Integer> list) {
        try {

            RobotConfig config = getRobotConfig(request);
            if (list.size() == 0 || config == null || config.getToken() == null || "".equals(config.getToken().trim())) {
                return false;
            }

            TuLingDell tuLingDell = new TuLingDell();
            tuLingDell.setApikey(config.getApikey());
            String[] arr = getTuLingToken(config.getToken());
            tuLingDell.setTimestamp(arr[1]);
            tuLingDell.setToken(arr[0]);

            TuLingDell.DataBean dataBean = new TuLingDell.DataBean();

            dataBean.setIds(list);
            dataBean.setClear(false);
            tuLingDell.setData(dataBean);
            String df = HttpUtil.sendPost(TuLingUtil.deleteUrl, JSON.toJSONString(tuLingDell));
            //根据图灵的主键集合进行删除
            JSONObject jsonObject = new JSONObject(df);
            if (jsonObject.getInt("code") == 0) {
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获得机器人私有语料库List
     *
     * @param request
     * @return
     */
    @RequestMapping("robotcorpuslist")
    public String getRobotCorpus(HttpServletRequest request, Integer p) {
        p = p != null && p > 1 ? p : 1;
        RobotCorpusExample example = new RobotCorpusExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andCorpusStatusNotEqualTo(EnumUtil.ROBOTCORPUS_STATUS.DEL.getStatus());

        request.setAttribute("count", corpusService.countByExample(example));
        example.setOrderByClause("id desc");
        example.setLimitStart((p - 1) * 20);
        example.setLimitEnd(20);
        List <RobotCorpus> list = corpusService.selectByExample(example);
        int i = (p - 1) * 20 + 1;
        for (RobotCorpus bean : list) {
            bean.setNumber(i++);
        }
        request.setAttribute("list", list);
        request.setAttribute("size", 20);
        request.setAttribute("p", p);

        fz();
//        查询学校是否开通此功能
        WechatThreeAuthorizeExample authorizeExample = new WechatThreeAuthorizeExample();
        authorizeExample.createCriteria().andLibraryIdEqualTo(getLibrary(request).getId());
        List <WechatThreeAuthorize> threeAuthorizes = authorizeService.selectByExample(authorizeExample);
        request.setAttribute("threeAuthorizes", threeAuthorizes);
        request.setAttribute("library_id", getLibrary(request).getId());
        request.setAttribute("three_pre_auth_code", WechatThreeConfigUtil.three_pre_auth_code);
        //获得语料库的基本配置

        request.setAttribute("config", getRobotConfig(request));

        return display(table + "_robotcorpuslist");
    }

    /**
     * 语料库查询
     *
     * @param request
     * @param keyWord
     * @return
     */
    @RequestMapping("searchRobotCorpus")
    public String searchRobotCorpus(HttpServletRequest request, String keyWord) {
        RobotCorpusExample example = new RobotCorpusExample();
        example.or().andLibraryidEqualTo(getLibrary(request).getId()).andAnswerLike("%" + keyWord.trim() + "%").andCorpusStatusNotEqualTo(EnumUtil.ROBOTCORPUS_STATUS.DEL.getStatus());
        example.or().andLibraryidEqualTo(getLibrary(request).getId()).andQuestionLike("%" + keyWord.trim() + "%").andCorpusStatusNotEqualTo(EnumUtil.ROBOTCORPUS_STATUS.DEL.getStatus());


        request.setAttribute("count", corpusService.countByExample(example));
        example.setOrderByClause("id desc");
        List <RobotCorpus> list = corpusService.selectByExample(example);
        int i = 1;
        for (RobotCorpus bean : list) {
            bean.setNumber(i++);
        }
        request.setAttribute("list", list);

        fz();

//        查询学校是否开通此功能
        WechatThreeAuthorizeExample authorizeExample = new WechatThreeAuthorizeExample();
        authorizeExample.createCriteria().andLibraryIdEqualTo(getLibrary(request).getId());
        List <WechatThreeAuthorize> threeAuthorizes = authorizeService.selectByExample(authorizeExample);
        request.setAttribute("threeAuthorizes", threeAuthorizes);
        request.setAttribute("library_id", getLibrary(request).getId());
        request.setAttribute("three_pre_auth_code", WechatThreeConfigUtil.three_pre_auth_code);
        request.setAttribute("keyWord", keyWord);
        request.setAttribute("config", getRobotConfig(request));
        return display(table + "_robotcorpuslist");
    }


    public void fz() {

        //        获取授权码，快速接入微信公众号
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.MINUTE, -8);
            if (WechatThreeConfigUtil.three_pre_auth_code == null || WechatThreeConfigUtil.three_pre_auth_code_create_time == null || calendar.getTime().after(WechatThreeConfigUtil.three_pre_auth_code_create_time)) {
                String preAuthCode = getPreAuthCode(WechatThreeConfigUtil.three_component_access_token);
                WechatThreeConfigUtil.three_pre_auth_code_create_time = new Date();
                WechatThreeConfigUtil.three_pre_auth_code = preAuthCode;
            }
        } catch (Exception e) {
            LogUtil.log("获取预授权码失败！");
        }

    }

    /**
     * 获取预授权码
     *
     * @param componentAccessToken
     * @return
     */
    public String getPreAuthCode(String componentAccessToken) throws IOException {
        Map <String, Object> parameter = new HashMap <>();
        parameter.put("component_appid", WechatThreeConfigUtil.three_app_id);
        String url = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token=" + componentAccessToken;
        String data = HttpUtil.sendPost(url, JSON.toJSON(parameter).toString());
        JSONObject jsonObject = new JSONObject(data);
        String preAuthCode = jsonObject.getString("pre_auth_code");
        int expiresIn = jsonObject.getInt("expires_in");
        return preAuthCode;
    }

    /**
     * 添加私有语料库
     *
     * @param request
     * @return
     */
    @RequestMapping("addRobotCorpus")
    public String addRobotCorpus(HttpServletRequest request) {


        return display(table + "_orpus_add");
    }

    /**
     * 添加操作私有语料库
     *
     * @param request
     * @param bean
     * @return
     */
    @RequestMapping("addDoRobotCorpus")
    @ResponseBody
    public Object addDoRobotCorpus(HttpServletRequest request, RobotCorpus bean) {

        RobotCorpusExample example = new RobotCorpusExample();
        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andQuestionEqualTo(bean.getQuestion()).andAnswerEqualTo(bean.getAnswer()).andCorpusStatusNotEqualTo(-1);
        int count = corpusService.countByExample(example);
        if (count != 0) {
            return ResultUtil.error("已有相关的语料，不允许重复添加！");
        }

        bean.setLibraryid(getLibrary(request).getId());
        bean.setCorpusStatus(EnumUtil.ROBOTCORPUS_STATUS.OPEN.getStatus());
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        RobotCorpus model = corpusService.add(bean);
        if (model != null) {
            List <RobotCorpus> listCorpus = new ArrayList <>();
            listCorpus.add(model);
            //将添加的语料库同步到图灵
            addTuLingCorpus(request, listCorpus);
            return ResultUtil.success(null, "添加成功！");
        } else {
            return ResultUtil.error("网络异常，请稍后重试！");
        }
    }

    //编辑语料库
    @RequestMapping("editRobotCorpus")
    private String editRobotCorpus(HttpServletRequest request, Long id) {
        RobotCorpus bean = corpusService.find(id);
        request.setAttribute("data", bean);
        return display(table + "editRobotCorpus");
    }


    //   编辑操作
    @RequestMapping("editDoRobotCorpus")
    @ResponseBody
    private Object editDoRobotCorpus(HttpServletRequest request, RobotCorpus bean) {

        String question1 = request.getParameter("question1");
        String question2 = request.getParameter("question2");
        String question3 = request.getParameter("question3");
        String question4 = request.getParameter("question4");
        String question5 = request.getParameter("question5");

        bean.setQuestion1(question1 != null ? question1 : "");
        bean.setQuestion2(question2 != null ? question2 : "");
        bean.setQuestion3(question3 != null ? question3 : "");
        bean.setQuestion4(question4 != null ? question4 : "");
        bean.setQuestion5(question5 != null ? question5 : "");

        bean.setUpdateTime(new Date());
        RobotCorpus corpus = corpusService.save(bean);
        if (corpus != null) {
            //TODO 编辑语料库执行先删除后新增
            List <Integer> list = new ArrayList <>();
            if (corpus.getTulingId()!=null) {
                list.add(Integer.parseInt(corpus.getTulingId()));
            }

            Boolean flag = delCorpus(request, list);
            if (flag) {
                //添加操作
                List <RobotCorpus> listCorpus = new ArrayList <>();
                listCorpus.add(corpus);
                //将添加的语料库同步到图灵
                addTuLingCorpus(request, listCorpus);

            }

            return ResultUtil.success(null, "操作成功！");
        } else {
            return ResultUtil.error("网络异常，请稍后重试！");
        }
    }

    /**
     * 删除，关闭，开启（批量）
     *
     * @param request
     * @param ids          要操作的语料的id，如1,2,,3
     * @param corpusStatus 1代表开启，0代表关闭，-1代表删除操作，详情请见ROBOTCORPUS_STATUS
     * @return
     */
    @RequestMapping("updateDoRobotCorpusStatus")
    @ResponseBody
    private Object updateDoRobotCorpusStatus(HttpServletRequest request, String ids, Integer corpusStatus) {
        String[] arr = ids.split(",");
        //关闭或删除，则删除对应的图灵语料库
        List <Long> ls = Arrays.stream(arr).map(Long::parseLong).collect(Collectors.toList());
        if (corpusStatus == 0 || corpusStatus == -1) {
            RobotCorpusExample example = new RobotCorpusExample();
            example.createCriteria().andIdIn(ls).andCorpusStatusEqualTo(1).andTulingIdIsNotNull();
            List <Integer> dellIds = corpusService.selectByExample(example).stream().map(RobotCorpus::getTulingId).map(Integer::parseInt).collect(Collectors.toList());
            Boolean flag = delCorpus(request, dellIds);
        }
        if (corpusStatus == 1) {
            //开启则需要在图灵语料库中进行新增
            //首先在数据库中查找对应的语料库
            RobotCorpusExample example = new RobotCorpusExample();
            example.createCriteria().andIdIn(ls).andCorpusStatusNotEqualTo(1);
            List <RobotCorpus> corpusList = corpusService.selectByExample(example);
            addTuLingCorpus(request, corpusList);

        }
        for (String id : arr) {
            RobotCorpus corpus = corpusService.find(Long.parseLong(id));
            corpus.setCorpusStatus(corpusStatus);
            corpusService.save(corpus);
        }

        return ResultUtil.success(null, "操作成功！");
    }

    /**
     * 验证问题是否重复
     *
     * @param request
     * @param question
     * @param id
     * @return 0代表未重复可添加，1代表不可添加
     */
    @RequestMapping("verifyTitle")
    @ResponseBody
    public Object verifyTitle(HttpServletRequest request, String question, Long id) {
        RobotCorpusExample example = new RobotCorpusExample();
        RobotCorpusExample.Criteria criteria = example.createCriteria();
        criteria.andCorpusStatusNotEqualTo(-1).andLibraryidEqualTo(getLibrary(request).getId()).andQuestionEqualTo(question.trim());
        if (id != null) {
            criteria.andIdNotEqualTo(id);
        }
        int count = corpusService.countByExample(example);
        return ResultUtil.success(count);
    }

    /**
     * 上传语料库
     *
     * @param request
     */
    @RequestMapping("uploadingRobotCorpus")
    @ResponseBody
    public Object uploadingRobotCorpus(HttpServletRequest request) {
//仅支持 xls格式的excel
        MultipartFile coverFile = null;
        List <RobotCorpus> list = new ArrayList <>();
        try {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            coverFile = multipartRequest.getFile("coverFile");
            if (!coverFile.isEmpty()) {
                System.out.println(coverFile.getName() + "+++if");
                Workbook wb = Workbook.getWorkbook(coverFile.getInputStream());
                Sheet rs = wb.getSheet(0);//wb.getSheet(0)
                int clos = rs.getColumns();//得到所有的列
                int rows = rs.getRows();//得到所有的行
                System.out.println(clos + " rows:" + rows);
                for (int i = 1; i < rows; i++) {
                    for (int j = 0; j < clos; j++) {
                        //第一个是列数，第二个是行数
                        RobotCorpus robotCorpus = new RobotCorpus();
                        robotCorpus.setQuestion(rs.getCell(j++, i).getContents());
                        robotCorpus.setAnswer(rs.getCell(j++, i).getContents());
                        robotCorpus.setQuestion1(rs.getCell(j++, i).getContents());
                        robotCorpus.setQuestion2(rs.getCell(j++, i).getContents());
                        robotCorpus.setQuestion3(rs.getCell(j++, i).getContents());
                        robotCorpus.setQuestion4(rs.getCell(j++, i).getContents());
                        robotCorpus.setQuestion5(rs.getCell(j++, i).getContents());

                        robotCorpus.setCreateTime(new Date());
                        robotCorpus.setUpdateTime(new Date());
                        robotCorpus.setLibraryid(getLibrary(request).getId());
                        if (list.size() < 1000) {
                            robotCorpus.setCorpusStatus(EnumUtil.ROBOTCORPUS_STATUS.OPEN.getStatus());
                        } else {
                            robotCorpus.setCorpusStatus(EnumUtil.ROBOTCORPUS_STATUS.CLOSE.getStatus());
                        }
                        list.add(robotCorpus);
                    }
                }
                //存放同步到图灵语料库的
                List <RobotCorpus> robotCorpuses = new ArrayList <>();
                if (list.size() == rows - 1) {
                    for (RobotCorpus robotCorpus : list) {
                        //对每一条数据进行判断，如果重复则不允许添加，以第一条数据为准
                        RobotCorpusExample example = new RobotCorpusExample();
                        example.createCriteria().andLibraryidEqualTo(getLibrary(request).getId()).andQuestionEqualTo(robotCorpus.getQuestion()).andAnswerEqualTo(robotCorpus.getAnswer()).andCorpusStatusNotEqualTo(-1);
                        int count = corpusService.countByExample(example);
                        if (count == 0) {
                            corpusService.add(robotCorpus);
                            robotCorpuses.add(robotCorpus);
                        }
                    }

                    addTuLingCorpus(request, robotCorpuses);
                    return ResultUtil.success(null, "导入成功");
                }

            }
        } catch (Exception e) {
            LogUtil.log(e.getMessage());
        }
        return ResultUtil.error("上传文件无效，请按模板填写试题信息，导入失败!");

    }

    /**
     * 下载语料库
     *
     * @param request
     */
    @RequestMapping("downloadRobotCorpus")
    @ResponseBody
    public Object downloadRobotCorpus(HttpServletRequest request, HttpServletResponse response, String template) {
        //写入Excel
        LogUtil.log("in excel...");
        WritableWorkbook book = null;
        try {
            //生成的Excel文件名
            String filename = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + "_robot_corpus.xls";
            // 打开文件
            book = Workbook.createWorkbook(response.getOutputStream());
            // 生成格式名称为："当前时间"的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("语料库", 0);
            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#0.00"); // 设置数字格式
            jxl.write.WritableCellFormat wcf = new jxl.write.WritableCellFormat(nf); // 设置表单格式
            wcf.setAlignment(Alignment.LEFT); // 设置为右对齐

            //表头
            sheet.addCell(new Label(0, 0, "问题【必填】（该行删除）"));
            sheet.addCell(new Label(1, 0, "答案【必填】（该行删除）"));
            sheet.addCell(new Label(2, 0, "相似问法1【选填】（该行删除)"));
            sheet.addCell(new Label(3, 0, "相似问法2【选填】（该行删除)"));
            sheet.addCell(new Label(4, 0, "相似问法3【选填】（该行删除)"));
            sheet.addCell(new Label(5, 0, "相似问法4【选填】（该行删除)"));
            sheet.addCell(new Label(6, 0, "相似问法5【选填】（该行删除)"));

            if (!"template".equals(template)) {
                //            获得需要导出的数据
                RobotCorpusExample example = new RobotCorpusExample();
                example.createCriteria().andCorpusStatusNotEqualTo(EnumUtil.ROBOTCORPUS_STATUS.DEL.getStatus()).andLibraryidEqualTo(getLibrary(request).getId());
                List <RobotCorpus> robotCorpusList = corpusService.selectByExample(example);
                if (robotCorpusList != null && !robotCorpusList.isEmpty()) {
                    for (int i = 0; i < robotCorpusList.size(); i++) {
                        sheet.setRowView(i, 400);  //设置行的高度
                        sheet.setColumnView(i, 25);  //设置列的宽度

                        sheet.addCell(new Label(0, i + 1, robotCorpusList.get(i).getQuestion()));
                        sheet.addCell(new Label(1, i + 1, robotCorpusList.get(i).getAnswer()));
                        sheet.addCell(new Label(2, i + 1, robotCorpusList.get(i).getQuestion1()));
                        sheet.addCell(new Label(3, i + 1, robotCorpusList.get(i).getQuestion2()));
                        sheet.addCell(new Label(4, i + 1, robotCorpusList.get(i).getQuestion3()));
                        sheet.addCell(new Label(5, i + 1, robotCorpusList.get(i).getQuestion4()));
                        sheet.addCell(new Label(6, i + 1, robotCorpusList.get(i).getQuestion5()));
                    }

                    sheet.setRowView(robotCorpusList.size(), 400);  //设置行的高度
                }

            }
            sheet.insertRow(0);//插入一行
            sheet.mergeCells(0, 0, 6, 0);//合并单元格
            sheet.addCell(new Label(0, 0, "导入规则说明：\n" +
                    "1.导入权限：【1000条/3次/天】/大于1000条的,只取前1000条\n" +
                    "2.文件大小不能超过5M;\n" +
                    "3.问题和答案为必填项，相似问题为选填项，相似问题添加的越多，机器人越聪明；\n" +
                    "4.问题的长度不超过64个字符； 答案不超过600个字符; \n+5.模板为固定格式，请按照规定格式填写"));
            //写入数据并关闭文件
            book.write();
            //防止乱码
            filename = new String(filename.getBytes(), "iso-8859-1");
            response.setCharacterEncoding("gb2312");
            response.reset();
            response.setContentType("application/OCTET-STREAM;charset=gb2312");
            response.setHeader("pragma", "no-cache");
            //点击导出excle按钮时候页面显示的默认名称
            response.addHeader("Content-Disposition", "attachment;filename=\"" + filename + ".xls\"");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LogUtil.log("关闭输出流！");
            if (book != null) {
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            LogUtil.log("导出结束！");
            return ResultUtil.success(null, "导出成功", "/library/" + table + "/list.html");

        }


    }


    //计算图灵官方的token
    public static String[] getTuLingToken(String api_secret) {


        String timestamp = System.currentTimeMillis() + "";
        return new String[]{MD5Util.MD5(api_secret + timestamp), timestamp};
    }
}
