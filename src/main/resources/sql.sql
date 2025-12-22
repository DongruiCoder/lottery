CREATE TABLE `lottery_draw_dlt`
(
    `id`                        BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键ID',

    `lottery_game_name`         VARCHAR(64) NOT NULL COMMENT '游戏名称',
    `lottery_game_num`          VARCHAR(32) NOT NULL COMMENT '游戏编号',
    `lottery_draw_num`          VARCHAR(32) NOT NULL COMMENT '期号',
    `lottery_draw_result`       VARCHAR(64) NOT NULL COMMENT '开奖结果（排序后）',
    `lottery_unsort_drawresult` VARCHAR(64)    DEFAULT NULL COMMENT '开奖结果（未排序）',
    `lottery_draw_time`         DATE        NOT NULL COMMENT '开奖日期',

    `pool_balance_afterdraw`    DECIMAL(20, 2) DEFAULT 0 COMMENT '开奖后奖池余额',
    `draw_flow_fund`            DECIMAL(20, 2) DEFAULT 0 COMMENT '调节基金支出',
    `total_sale_amount`         DECIMAL(20, 2) DEFAULT 0 COMMENT '销售总额',
    `draw_pdf_url`              VARCHAR(255)   DEFAULT NULL COMMENT '开奖公告PDF',

    `front1`                    INT,
    `front2`                    INT,
    `front3`                    INT,
    `front4`                    INT,
    `front5`                    INT,
    `back1`                     INT,
    `back2`                     INT,

    `first_prize_count`         INT            DEFAULT 0,
    `first_prize_amount`        DECIMAL(20, 2) DEFAULT 0,
    `first_prize_total`         DECIMAL(20, 2) DEFAULT 0,

    `first_prize_extra_count`   INT            DEFAULT 0,
    `first_prize_extra_amount`  DECIMAL(20, 2) DEFAULT 0,
    `first_prize_extra_total`   DECIMAL(20, 2) DEFAULT 0,

    `second_prize_count`        INT            DEFAULT 0,
    `second_prize_amount`       DECIMAL(20, 2) DEFAULT 0,
    `second_prize_total`        DECIMAL(20, 2) DEFAULT 0,

    `second_prize_extra_count`  INT            DEFAULT 0,
    `second_prize_extra_amount` DECIMAL(20, 2) DEFAULT 0,
    `second_prize_extra_total`  DECIMAL(20, 2) DEFAULT 0,

    `third_prize_count`         INT            DEFAULT 0,
    `third_prize_amount`        DECIMAL(20, 2) DEFAULT 0,
    `third_prize_total`         DECIMAL(20, 2) DEFAULT 0,

    `fourth_prize_count`        INT            DEFAULT 0,
    `fourth_prize_amount`       DECIMAL(20, 2) DEFAULT 0,
    `fourth_prize_total`        DECIMAL(20, 2) DEFAULT 0,

    `fifth_prize_count`         INT            DEFAULT 0,
    `fifth_prize_amount`        DECIMAL(20, 2) DEFAULT 0,
    `fifth_prize_total`         DECIMAL(20, 2) DEFAULT 0,

    `sixth_prize_count`         INT            DEFAULT 0,
    `sixth_prize_amount`        DECIMAL(20, 2) DEFAULT 0,
    `sixth_prize_total`         DECIMAL(20, 2) DEFAULT 0,

    `seventh_prize_count`       INT            DEFAULT 0,
    `seventh_prize_amount`      DECIMAL(20, 2) DEFAULT 0,
    `seventh_prize_total`       DECIMAL(20, 2) DEFAULT 0,

    `eighth_prize_count`        INT            DEFAULT 0,
    `eighth_prize_amount`       DECIMAL(20, 2) DEFAULT 0,
    `eighth_prize_total`        DECIMAL(20, 2) DEFAULT 0,

    `ninth_prize_count`         INT            DEFAULT 0,
    `ninth_prize_amount`        DECIMAL(20, 2) DEFAULT 0,
    `ninth_prize_total`         DECIMAL(20, 2) DEFAULT 0,

    PRIMARY KEY (`id`),

    -- 保证唯一性：同一个游戏、同一期，只能有一条记录
    UNIQUE KEY `uniq_game_draw` (`lottery_game_num`, `lottery_draw_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='大乐透开奖完整数据';


CREATE TABLE `lottery_draw_p3`
(
    `id`                      bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `lottery_draw_num`        varchar(20) NOT NULL COMMENT '期号',
    `lottery_draw_time`       date        NOT NULL COMMENT '开奖日期',
    `lottery_draw_result_raw` varchar(20) NOT NULL COMMENT '原始开奖结果，如：0 7 9',
    `draw_num_1`              tinyint(4) NOT NULL COMMENT '开奖号码第1位',
    `draw_num_2`              tinyint(4) NOT NULL COMMENT '开奖号码第2位',
    `draw_num_3`              tinyint(4) NOT NULL COMMENT '开奖号码第3位',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_draw_num` (`lottery_draw_num`),
    KEY                       `idx_draw_nums` (`draw_num_1`,`draw_num_2`,`draw_num_3`)
) ENGINE=InnoDB AUTO_INCREMENT=7463 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='排列三开奖期次表（含原始结果）';