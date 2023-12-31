package me.lkhz.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CouponDAO_01 implements CouponDAO{
    final String URL = "jdbc:mysql://localhost:3306/study_db";
    final String USER_ID = "appadmin";
    final String USER_PW = "P@ssw0rd";

    /**
     * 쿠폰 발행 메서드
     *  1. coupon 테이블에 잔여쿠폰 개수 -1
     *  2. coupon history 테이블에 여태껏 발행된 쿠폰 개수 +1
     */
    @Override
    public void getCoupon() {
        System.out.println("### CouponDAO.getCoupon Start");
        try {
            Connection conn = DriverManager.getConnection(URL, USER_ID, USER_PW);
            conn.setAutoCommit(false);
            try (conn) {
                try(Statement stmt = conn.createStatement()){
                    String querry = "UPDATE COUPON                              \n" +
                            "SET REMAIN_COUNT = REMAIN_COUNT - 1        \n" +
                            "WHERE ID = 1                               \n" +
                            "  AND REMAIN_COUNT > 0                       ";

                    stmt.executeUpdate(querry);
                } catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                }
                //Thread.sleep(10000);


                try(Statement stmt = conn.createStatement()){
                    String querry = "UPDATE COUPON_HISTORY                                    \n" +
                            "SET issued_coupon_count = issued_coupon_count + 1        \n" +
                            "WHERE ID = 1                               \n";

                    stmt.executeUpdate(querry);
                } catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("### CouponDAO.getCoupon End");
    }


    /**
     * 쿠폰 취소 메서드
     *  1. coupon history 테이블에 여태껏 발행된 쿠폰 개수 -1
     *  2. coupon 테이블에 잔여쿠폰 개수 +1
     */
    @Override
    public void cancleCoupon(){
        System.out.println("### CouponDAO.cancleCoupon Start");
        try {
            Connection conn = DriverManager.getConnection(URL, USER_ID, USER_PW);
            conn.setAutoCommit(false);
            try (conn) {

                try(Statement stmt = conn.createStatement()){

                    String querry = "UPDATE COUPON_HISTORY                                    \n" +
                            "SET issued_coupon_count = issued_coupon_count - 1        \n" +
                            "WHERE ID = 1                               \n";

                    stmt.executeUpdate(querry);
                } catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                }

                try(Statement stmt = conn.createStatement()){
                    String querry = "UPDATE COUPON                              \n" +
                            "SET REMAIN_COUNT = REMAIN_COUNT + 1        \n" +
                            "WHERE ID = 1                               \n";

                    stmt.executeUpdate(querry);
                } catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("### CouponDAO.cancleCoupon End");
    }


    public void insertCoupon() {
        System.out.println("### CouponDAO.insertCoupon Start");
        try {
            Connection conn = DriverManager.getConnection(URL, USER_ID, USER_PW);
            conn.setAutoCommit(false);
            try (conn) {
                try(Statement stmt = conn.createStatement()){
                    String querry = "INSERT INTO COUPON  VALUES(2, 1000)                                  \n";

                    stmt.executeUpdate(querry);
                } catch (SQLException e){
                    e.printStackTrace();
                    conn.rollback();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("### CouponDAO.insertCoupon End");
    }
}
