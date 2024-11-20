package com.ncash.productservice.config;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.ncash.productservice.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;


@ChangeLog
public class DatabaseChangLog {

    @ChangeSet(order = "002", id = "1732077703122", author = "babu")
    public void createIndex(MongoTemplate mongoTemplate) {
        mongoTemplate.indexOps(Product.class)
                .ensureIndex(new Index().on("productId", Sort.Direction.ASC).named("productId_index"));
    }
}
