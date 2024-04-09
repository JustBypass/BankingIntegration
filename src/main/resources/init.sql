CREATE TABLE transaction_left(
   account_from bigint,
   account_to bigint,
   lim_date TIMESTAMP WITH TIME ZONE,
   limit_currency_shortname varchar(3),
   lim NUMERIC(10, 2) default 1000,
   expense_category varchar(20),
   primary key(account_from,account_to,lim_date,expense_category)
);

CREATE TABLE transaction_right(
   t2_id SERIAL PRIMARY KEY,
   account_from bigint,
   account_to bigint,
   --lim_date TIMESTAMP WITH TIME ZONE,
   tr_date TIMESTAMP WITH TIME ZONE,
   tr_sum NUMERIC(10, 2),
   expense_category varchar(20)
   --foreign key(account_from,account_to,expense_category)
  --     references transaction_left(account_from,account_to,expense_category)
);



CREATE VIEW aggregated AS(
    with transaction_aggregation as(
        SELECT
               t2.t2_id,
               t1.account_from, t1.account_to,
               t1.lim,
               t1.lim_date,
               t1.expense_category,
               t2.tr_date,
               t2.tr_sum,
               t1.limit_currency_shortname
        FROM transaction_left t1
                 INNER JOIN transaction_right t2 ON t1.account_from = t2.account_from
            AND t1.account_to = t2.account_to
            AND t1.expense_category = t2.expense_category
            WHERE t1.lim_date = (
                        SELECT transaction_left.lim_date FROM transaction_left
                            INNER JOIN transaction_right
                            ON transaction_left.account_from = transaction_right.account_from
                            AND transaction_left.account_to = transaction_right.account_to
                            AND transaction_left.expense_category = transaction_right.expense_category
                                ORDER BY transaction_left.lim_date DESC LIMIT 1
                    )

    )
    SELECT
        t2_id,
        account_from,
        account_to,
        limit_currency_shortname,
        lim_date,
        tr_date,
        tr_sum,
        expense_category,
        lim,
        CASE
            WHEN
                lim - (
                WITH numeration AS (
                    SELECT *, ROW_NUMBER() OVER ( ORDER BY t2_id) AS row_num
                    FROM transaction_aggregation ta
                    where
                       ta.account_to = a.account_to
                      AND ta.account_from = a.account_from
                      AND ta.expense_category = a.expense_category
                      AND ta.lim_date = a.lim_date

                      ORDER BY t2_id DESC
                )
                SELECT sum(tr_sum)
                FROM numeration n
                  WHERE row_num <= (SELECT row_num FROM numeration
                                        WHERE t2_id = a.t2_id )
                      GROUP BY n.account_from,
                        n.account_to,
                        n.lim_date,
                        n.expense_category

                ) >= 0

                THEN TRUE
            ELSE FALSE
            END AS limit_exceeded
    FROM transaction_aggregation a
    ORDER BY lim_date
);



