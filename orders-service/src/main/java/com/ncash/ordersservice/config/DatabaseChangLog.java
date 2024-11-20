package com.ncash.ordersservice.config;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.ncash.ordersservice.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;


@ChangeLog
public class DatabaseChangLog {

    @ChangeSet(order = "004", id = "1732077703145", author = "babu")
    public void createIndex(MongoTemplate mongoTemplate) {
        mongoTemplate.indexOps(Order.class)
                .ensureIndex(new Index().on("orderId", Sort.Direction.ASC).named("orderId_index"));
    }
}
