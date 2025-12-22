package com.businiao.lottery;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class WW2YxLInsert {

    public static void main(String[] args) throws Exception {
        var sportteryClient = new SportteryClient();

        for (int i = 0; i <= 2000; i++) {
            String json = sportteryClient.wW2YxL();
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
        JsonNode fields = root.path("data").path("form").path("fields");

        String insertSql = "INSERT INTO quiz_questions(label, question_type, correct_answers, choices) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                "correct_answers = VALUES(correct_answers)," +
                "choices = VALUES(choices);";

        try (Connection conn = DriverManager.getConnection(Global.JDBC_URL, Global.JDBC_USER, Global.JDBC_PASSWORD);
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            for (JsonNode field : fields) {
                try {
                    String label = field.get("label").asText();
                    String questionType = field.get("subSystemExtraAttrs").get("questionType").asText();

                    // 正确答案
                    JsonNode correctAnswersNode = field.get("subSystemExtraAttrs").get("correctAnswers");
                    List<String> correctAnswersList = new ArrayList<>();
                    for (JsonNode ans : correctAnswersNode) {
                        correctAnswersList.add(ans.asText());
                    }
                    String correctAnswers = String.join(",", correctAnswersList);

                    // 选项，包含 value 和 name
                    JsonNode choicesNode = field.get("choices");
                    List<ObjectNode> choiceList = new ArrayList<>();
                    for (JsonNode choice : choicesNode) {
                        ObjectNode choiceObj = mapper.createObjectNode();
                        choiceObj.put("value", choice.get("value").asText());
                        choiceObj.put("name", choice.get("name").asText());
                        choiceList.add(choiceObj);
                    }
                    String choices = mapper.writeValueAsString(choiceList); // 存 JSON 格式

                    ps.setString(1, label);
                    ps.setString(2, questionType);
                    ps.setString(3, correctAnswers);
                    ps.setString(4, choices);

                    ps.addBatch();
                } catch (Exception e) {
                    System.out.println(field);
                    throw new Exception(e);
                }

            }

            ps.executeBatch();
        }
    }
}
