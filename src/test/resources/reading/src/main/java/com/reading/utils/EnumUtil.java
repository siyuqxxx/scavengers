package com.reading.utils;

import java.util.ArrayList;
import java.util.List;

public class EnumUtil {

    public enum DEFAULT_PAGE {

        APP(10), APP_BOOK_SIZE(3);

        private final int size;

        DEFAULT_PAGE(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }
    }

    /**
     * 考研座位审批状态
     */
    public enum SEAT_APPLY_STATUS {

        wait(1), agree(2), refuse(3);
        private final int status;

        SEAT_APPLY_STATUS(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    /**
     * 判断是否可以修改（研讨室配置）
     */
    public enum UPDATE_STATUS {

        allow(1), forbid(2);

        private final int status;

        UPDATE_STATUS(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    public enum ROBOTCORPUS_STATUS {
        CLOSE(0), OPEN(1), DEL(-1);

        ROBOTCORPUS_STATUS(int status) {
            this.status = status;
        }

        private final int status;

        public int getStatus() {
            return status;
        }

    }

    //    短信消费种类
    public enum MESSAGERECORDSTATUS {
        borrow(1), appointment(2);

        MESSAGERECORDSTATUS(int status) {
            this.status = status;
        }

        private final int status;

        public int getStatus() {
            return status;
        }
    }

    //    公众号授权状态
    public enum WechatThreeAuthorize {
        authorized(1), unauthorized(2), updateauthorized(3);

        WechatThreeAuthorize(int status) {
            this.status = status;
        }

        private final int status;

        public int getStatus() {
            return status;
        }
    }

    /**
     * 星期一到星期天
     * Monday，Tuesday、Wednesday、Thursday、Friday、Saturday 、Sunday
     */

    public enum WEEKLY {
        DEFAULT("DEFAULT", "默认"),
        MONDAY("MONDAY", "周一"),
        TUESDAY("TUESDAY", "周二"),
        WEDNESDAY("WEDNESDAY", "周三"),
        THURSDAY("THURSDAY", "周四"),
        FRIDAY("FRIDAY", "周五"),
        SATURDAY("SATURDAY", "周六"),
        SUNDAY("SUNDAY", "周日");

        private String code;
        private String value;


        WEEKLY(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public String toString() {
            return "[" + this.code + "]" + this.value;
        }

        public static String getValueByCode(String code) {
            for (WEEKLY mode : WEEKLY.values()) {
                if (mode.code.equals(code)) {
                    return mode.value;
                }
            }
            return null;
        }

        public WEEKLY getByCode(String code) {
            for (WEEKLY mode : WEEKLY.values()) {
                if (mode.code == code) {
                    return mode;
                }
            }
            return null;
        }
    }

    public enum LibraryParameterSetByType {

        SeatExamRule("seat_exam_rule", "考研座位预约规则"),

        ShelfApplyRule("shelf_apply_rule", "书柜预约规则");

        private final String type;

        private final String typeExplain;

        LibraryParameterSetByType(String type, String typeExplain) {
            this.type = type;
            this.typeExplain = typeExplain;
        }

        public String getType() {
            return type;
        }
    }

    public enum LIS_AUTHOR_ENUM {

        ALEPH(1l, "ALEPH", "wau"), HUI_WEN(2l, "汇文", "03"), KE_TU(4l, "科图ILAS", "5"), JIN_PAN(5l, "金盘", "auther"), MIAO_SI(6l, "妙思", "2"),

        DP2(7l, "DP2", "Author"), qing_da_xin_yang(8l, "清大新洋", "author"), SHEN_DA(9l, "深大", "author"), SHUWOJINPAN(10l, "书蜗金盘", "3");
        private final Long SYSTEM_ID;

        private final String SYSTEM_NAME;

        private final String AUTHOR_MARK;

        LIS_AUTHOR_ENUM(Long SYSTEM_ID, String SYSTEM_NAME, String AUTHOR_MARK) {
            this.SYSTEM_ID = SYSTEM_ID;
            this.SYSTEM_NAME = SYSTEM_NAME;
            this.AUTHOR_MARK = AUTHOR_MARK;
        }

        public Long getSYSTEM_ID() {
            return SYSTEM_ID;
        }

        public String getSYSTEM_NAME() {
            return SYSTEM_NAME;
        }

        public String getAUTHOR_MARK() {
            return AUTHOR_MARK;
        }

        public static LIS_AUTHOR_ENUM matchSystem(Long system_id) {
            for (LIS_AUTHOR_ENUM item : LIS_AUTHOR_ENUM.values()) {
                if (item.SYSTEM_ID.equals(system_id)) {
                    return item;
                }
            }
            return null;
        }
    }


    public enum ROOM_TYPE {
        ordinary_seat("1", "普通座位预约"), exam_seat("2", "考研座位预约"), collection("3", "馆藏地");//馆藏


        ROOM_TYPE(String type_value, String type_name) {
            this.type_value = type_value;
            this.type_name = type_name;
        }

        private final String type_value;

        private final String type_name;

        public String getType_value() {
            return type_value;
        }

        public String getType_name() {
            return type_name;
        }
    }

    //猜你喜欢模型
    public enum GUESS_YOU_LIKE_SOURCE_MODEL_ENUM {

        borrow("1", "借阅"), bookcollection("2", "图书收藏"), foot_print("3", "浏览足迹"), recommend("4", "荐购"), label("5", "标签");

        private final String number;

        private final String type_name;

        GUESS_YOU_LIKE_SOURCE_MODEL_ENUM(String number, String type_name) {
            this.number = number;
            this.type_name = type_name;
        }

        public String getNumber() {
            return number;
        }

        public String getType_name() {
            return type_name;
        }

        public static GUESS_YOU_LIKE_SOURCE_MODEL_ENUM getGUESS_YOU_LIKE_SOURCE_MODEL_ENUM(String str) {

            for (GUESS_YOU_LIKE_SOURCE_MODEL_ENUM em : GUESS_YOU_LIKE_SOURCE_MODEL_ENUM.values()) {
                if (em.getNumber().equals(str)) {
                    return em;
                }
            }
            return null;
        }

    }

    //猜你喜欢类型
    public enum GUESS_YOU_LIKE_TYPE_ENUM {
        /**
         * 1、喜欢作者的人正在读
         * 2、分类，根据借阅推荐
         * 3、正在看此书的人正在看
         */
        author("1", "作者","如:检索作者写的其他的书"), book_classify("2", "图书分类","如:根据图书的分类进行检索"), book_name("3", "书名","如:检索读过此书的人正在读");

        private final String number;

        private final String type_name;

        private final String mark;

        GUESS_YOU_LIKE_TYPE_ENUM(String number, String type_name, String mark) {
            this.number = number;
            this.type_name = type_name;
            this.mark = mark;
        }

        public String getMark() {
            return mark;
        }

        public String getNumber() {
            return number;
        }

        public String getType_name() {
            return type_name;
        }

        public static GUESS_YOU_LIKE_TYPE_ENUM getGUESS_YOU_LIKE_TYPE_ENUM(String str) {
            for (GUESS_YOU_LIKE_TYPE_ENUM em : GUESS_YOU_LIKE_TYPE_ENUM.values()) {
                if (em.getNumber().equals(str)) {
                    return em;
                }

            }
            return null;
        }
    }

    /**
     * ibeacon 生产厂家
     */
    public enum IBEACON_MANUFACTURER{
        XUN_XIN_DIAN_ZI(1,"寻信电子"),YUN_LI_WU_LI(2,"云里雾里");
        private final Integer MANUFACTURER_ID;
        private final String MANUFACTURER_NAME ;

        public long getMANUFACTURER_ID() {
            return MANUFACTURER_ID;
        }

        public String getMANUFACTURER_NAME() {
            return MANUFACTURER_NAME;
        }

        IBEACON_MANUFACTURER(Integer MANUFACTURER_ID, String MANUFACTURER_NAME){
            this.MANUFACTURER_ID = MANUFACTURER_ID;
            this.MANUFACTURER_NAME = MANUFACTURER_NAME ;
        }

        public static String getNameById(Integer id){
            for(IBEACON_MANUFACTURER temp : IBEACON_MANUFACTURER.values()){
                if(temp.getMANUFACTURER_ID() == id){
                    return temp.MANUFACTURER_NAME ;
                }
            }
            return null ;
        }
        public static List<Integer> getManufacturerIds(){
            List list = new ArrayList();
            for(IBEACON_MANUFACTURER temp : IBEACON_MANUFACTURER.values()){
                list.add(temp.getMANUFACTURER_ID());
            }
            return list ;
        }
    }

    public enum CHOOSE_BOOK_PAGE {

        APP_SIZE(10), PC_SIZE(30);

        private final int size;

        CHOOSE_BOOK_PAGE(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }
    }
}