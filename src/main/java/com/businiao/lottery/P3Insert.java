package com.businiao.lottery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class P3Insert {
    public static void main(String[] args) throws Exception {
        SportteryClient client = new SportteryClient();
        for (int i = 253; i <= 300; i++) {
            String json = client.getHistoryPageList(35, i);
            insert(json);
            System.out.println("排列三数据插入完成 ---- page " + i);
        }
    }

    private static void insert(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        JsonNode list = root.path("value").path("list");

        String insertSql = """
                    INSERT INTO lottery_draw_p3 (
                        lottery_draw_num,
                        lottery_draw_result_raw,
                        draw_num_1,
                        draw_num_2,
                        draw_num_3,
                        lottery_draw_time              
                    ) VALUES (?, ?, ?, ?, ?, ?)
                    ON DUPLICATE KEY UPDATE
                        lottery_draw_result_raw = VALUES(lottery_draw_result_raw),
                        draw_num_1 = VALUES(draw_num_1),
                        draw_num_2 = VALUES(draw_num_2),
                        draw_num_3 = VALUES(draw_num_3),
                        lottery_draw_time = VALUES(lottery_draw_time)         
                """;

        try (Connection conn = DriverManager.getConnection(Global.JDBC_URL, Global.JDBC_USER, Global.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            for (JsonNode draw : list) {
                try {
                    String drawNum = draw.path("lotteryDrawNum").asText();
                    String rawResult = draw.path("lotteryDrawResult").asText();

                    int[] nums = parseDrawNums(rawResult);

                    ps.setString(1, drawNum);
                    ps.setString(2, rawResult);
                    ps.setInt(3, nums[0]);
                    ps.setInt(4, nums[1]);
                    ps.setInt(5, nums[2]);

                    ps.setDate(6, Date.valueOf(draw.path("lotteryDrawTime").asText()));

                    ps.addBatch();
                } catch (Exception e) {
                    System.out.println("异常数据：" + draw);
                    throw e;
                }
            }

            ps.executeBatch();
        }
    }

    /**
     * 解析排列三号码，只取前 3 位
     */
    private static int[] parseDrawNums(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new IllegalArgumentException("empty draw result");
        }
        String[] parts = raw.trim().split("\\s+");
        if (parts.length < 3) {
            throw new IllegalArgumentException("invalid draw result: " + raw);
        }
        return new int[]{
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        };
    }

}
