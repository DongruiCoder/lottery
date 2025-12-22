package com.businiao.lottery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DLTInsert {

    public static void main(String[] args) throws Exception {
        var sportteryClient = new SportteryClient();

        for (int i = 1; i <= 100; i++) {
            String json = sportteryClient.getHistoryPageList(85, i);
            try {
                insert(json);
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception(e);
            }
            System.out.println("数据插入完成！---------------- " + i);
        }
    }

    private static void insert(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode list = root.path("value").path("list");

        String insertSql = "INSERT INTO lottery_draw_dlt (\n" +
                "    lottery_game_name,\n" +
                "    lottery_game_num,\n" +
                "    lottery_draw_num,\n" +
                "    lottery_draw_result,\n" +
                "    lottery_unsort_drawresult,\n" +
                "    lottery_draw_time,\n" +
                "    pool_balance_afterdraw,\n" +
                "    draw_flow_fund,\n" +
                "    total_sale_amount,\n" +
                "    draw_pdf_url,\n" +
                "    front1, front2, front3, front4, front5,\n" +
                "    back1, back2,\n" +
                "    first_prize_count, first_prize_amount, first_prize_total,\n" +
                "    first_prize_extra_count, first_prize_extra_amount, first_prize_extra_total,\n" +
                "    second_prize_count, second_prize_amount, second_prize_total,\n" +
                "    second_prize_extra_count, second_prize_extra_amount, second_prize_extra_total,\n" +
                "    third_prize_count, third_prize_amount, third_prize_total,\n" +
                "    fourth_prize_count, fourth_prize_amount, fourth_prize_total,\n" +
                "    fifth_prize_count, fifth_prize_amount, fifth_prize_total,\n" +
                "    sixth_prize_count, sixth_prize_amount, sixth_prize_total,\n" +
                "    seventh_prize_count, seventh_prize_amount, seventh_prize_total,\n" +
                "    eighth_prize_count, eighth_prize_amount, eighth_prize_total,\n" +
                "    ninth_prize_count, ninth_prize_amount, ninth_prize_total\n" +
                ") VALUES (\n" +
                "    ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,\n" +
                "    ?, ?, ?, ?, ?,\n" +
                "    ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?,\n" +
                "    ?, ?, ?\n" +
                ")\n" +
                "ON DUPLICATE KEY UPDATE\n" +
                "    lottery_game_name = VALUES(lottery_game_name),\n" +
                "    lottery_game_num = VALUES(lottery_game_num),\n" +
                "    lottery_draw_result = VALUES(lottery_draw_result),\n" +
                "    lottery_unsort_drawresult = VALUES(lottery_unsort_drawresult),\n" +
                "    lottery_draw_time = VALUES(lottery_draw_time),\n" +
                "    pool_balance_afterdraw = VALUES(pool_balance_afterdraw),\n" +
                "    draw_flow_fund = VALUES(draw_flow_fund),\n" +
                "    total_sale_amount = VALUES(total_sale_amount),\n" +
                "    draw_pdf_url = VALUES(draw_pdf_url),\n" +
                "    front1 = VALUES(front1),\n" +
                "    front2 = VALUES(front2),\n" +
                "    front3 = VALUES(front3),\n" +
                "    front4 = VALUES(front4),\n" +
                "    front5 = VALUES(front5),\n" +
                "    back1 = VALUES(back1),\n" +
                "    back2 = VALUES(back2),\n" +
                "    first_prize_count = VALUES(first_prize_count),\n" +
                "    first_prize_amount = VALUES(first_prize_amount),\n" +
                "    first_prize_total = VALUES(first_prize_total),\n" +
                "    first_prize_extra_count = VALUES(first_prize_extra_count),\n" +
                "    first_prize_extra_amount = VALUES(first_prize_extra_amount),\n" +
                "    first_prize_extra_total = VALUES(first_prize_extra_total),\n" +
                "    second_prize_count = VALUES(second_prize_count),\n" +
                "    second_prize_amount = VALUES(second_prize_amount),\n" +
                "    second_prize_total = VALUES(second_prize_total),\n" +
                "    second_prize_extra_count = VALUES(second_prize_extra_count),\n" +
                "    second_prize_extra_amount = VALUES(second_prize_extra_amount),\n" +
                "    second_prize_extra_total = VALUES(second_prize_extra_total),\n" +
                "    third_prize_count = VALUES(third_prize_count),\n" +
                "    third_prize_amount = VALUES(third_prize_amount),\n" +
                "    third_prize_total = VALUES(third_prize_total),\n" +
                "    fourth_prize_count = VALUES(fourth_prize_count),\n" +
                "    fourth_prize_amount = VALUES(fourth_prize_amount),\n" +
                "    fourth_prize_total = VALUES(fourth_prize_total),\n" +
                "    fifth_prize_count = VALUES(fifth_prize_count),\n" +
                "    fifth_prize_amount = VALUES(fifth_prize_amount),\n" +
                "    fifth_prize_total = VALUES(fifth_prize_total),\n" +
                "    sixth_prize_count = VALUES(sixth_prize_count),\n" +
                "    sixth_prize_amount = VALUES(sixth_prize_amount),\n" +
                "    sixth_prize_total = VALUES(sixth_prize_total),\n" +
                "    seventh_prize_count = VALUES(seventh_prize_count),\n" +
                "    seventh_prize_amount = VALUES(seventh_prize_amount),\n" +
                "    seventh_prize_total = VALUES(seventh_prize_total),\n" +
                "    eighth_prize_count = VALUES(eighth_prize_count),\n" +
                "    eighth_prize_amount = VALUES(eighth_prize_amount),\n" +
                "    eighth_prize_total = VALUES(eighth_prize_total),\n" +
                "    ninth_prize_count = VALUES(ninth_prize_count),\n" +
                "    ninth_prize_amount = VALUES(ninth_prize_amount),\n" +
                "    ninth_prize_total = VALUES(ninth_prize_total);";

        try (Connection conn = DriverManager.getConnection(Global.JDBC_URL, Global.JDBC_USER, Global.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            for (JsonNode draw : list) {
                try {
                    ps.setString(1, draw.path("lotteryGameName").asText());
                    ps.setString(2, draw.path("lotteryGameNum").asText());
                    ps.setString(3, draw.path("lotteryDrawNum").asText());
                    ps.setString(4, draw.path("lotteryDrawResult").asText());
                    ps.setString(5, draw.path("lotteryUnsortDrawresult").asText());
                    ps.setDate(6, Date.valueOf(draw.path("lotteryDrawTime").asText()));

                    ps.setBigDecimal(7, parseBigDecimal(draw.path("poolBalanceAfterdraw")));
                    ps.setBigDecimal(8, parseBigDecimal(draw.path("drawFlowFund")));
                    ps.setBigDecimal(9, parseBigDecimal(draw.path("totalSaleAmount")));
                    ps.setString(10, draw.path("drawPdfUrl").asText());

                    // 拆前区后区号码
                    String[] nums = draw.path("lotteryDrawResult").asText().split(" ");
                    ps.setInt(11, Integer.parseInt(nums[0]));
                    ps.setInt(12, Integer.parseInt(nums[1]));
                    ps.setInt(13, Integer.parseInt(nums[2]));
                    ps.setInt(14, Integer.parseInt(nums[3]));
                    ps.setInt(15, Integer.parseInt(nums[4]));
                    ps.setInt(16, Integer.parseInt(nums[5]));
                    ps.setInt(17, Integer.parseInt(nums[6]));

                    // 奖级信息
                    JsonNode prizeList = draw.path("prizeLevelList");
                    for (int i = 0; i < 11; i++) {
                        JsonNode prize = prizeList.get(i);
                        ps.setInt(18 + i * 3, parseInt(prize.path("stakeCount")));
                        ps.setBigDecimal(19 + i * 3, parseBigDecimal(prize.path("stakeAmountFormat")));
                        ps.setBigDecimal(20 + i * 3, parseBigDecimal(prize.path("totalPrizeamount")));
                    }

                    ps.addBatch();
                } catch (Exception e) {
                    System.out.println(draw);
                    throw new Exception(e);
                }

            }

            ps.executeBatch();
        }
    }

    private static int parseInt(JsonNode node) {
        if (node == null) return 0;
        String text = node.asText().trim();
        if (text.isEmpty() || text.equals("---")) {
            return 0;
        }
        try {
            return Integer.parseInt(text.replace(",", ""));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static BigDecimal parseBigDecimal(JsonNode node) {
        if (node == null) return BigDecimal.ZERO;
        String text = node.asText().replace(",", "").trim();
        if (text.isEmpty() || text.equals("---")) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
}
