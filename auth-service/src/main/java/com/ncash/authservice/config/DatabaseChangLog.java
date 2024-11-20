package com.ncash.authservice.config;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.ncash.authservice.entity.UserCredential;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;


@ChangeLog
public class DatabaseChangLog {

    @ChangeSet(order = "002", id = "1732077703121", author = "babu")
    public void createIndex(MongoTemplate mongoTemplate) {
        mongoTemplate.indexOps(UserCredential.class)
                .ensureIndex(new Index().on("id", Sort.Direction.ASC).named("id_index"));
    }
}
