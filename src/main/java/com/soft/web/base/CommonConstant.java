package com.soft.web.base;

public class CommonConstant {
	
    /**
     * 세션 관련 상수값
     */
    public static class session {
        /** 세션에 저장되는 사용자정보 */
        public static final String SESSION_KEY_USER = "_sessionUser";
        
        /** 세션에 저장되는 관리자정보 */
        public static final String SESSION_KEY_ADMIN = "_sessionAdmin";
        
        /** 세션에 저장되는 데스크 관리자정보 */
        public static final String SESSION_KEY_DESK_ADMIN = "_sessionDeskAdmin";
        
        /** 세션에 저장되는 카트번호 */
        public static final String SESSION_KEY_CARTNO = "_sessionCartno";
        
        /** 세션에 저장되는 카트 카운터정보 */
        public static final String SESSION_KEY_CART_COUNT = "_sessionCartCount";
        
        /** 세션에 저장되는 카트 카운터정보 */
        public static final String SESSION_KEY_WISH_COUNT = "_sessionWishCount";
        
        /** 세션에 저장되는 G포인트유저 */
        public static final String SESSION_KEY_GPOINT_USER = "_sessionGpointUser";

        /** 세션 타임아웃(초) */
        public static final int SESSION_TIMEOUT_SECOND = 60 * 60;
    }
}
