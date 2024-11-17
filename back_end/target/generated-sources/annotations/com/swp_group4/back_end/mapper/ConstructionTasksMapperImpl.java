package com.swp_group4.back_end.mapper;

import com.swp_group4.back_end.entities.ConstructionTasks;
import com.swp_group4.back_end.responses.ConstructTaskStatusResponse;
import com.swp_group4.back_end.responses.DeadlineConstructionResponse;
import com.swp_group4.back_end.responses.ListConstructProgressResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class ConstructionTasksMapperImpl implements ConstructionTasksMapper {

    @Override
    public DeadlineConstructionResponse mapDeadlineConstructionResponse(ConstructionTasks deadlineConstructionRequest) {
        if ( deadlineConstructionRequest == null ) {
            return null;
        }

        DeadlineConstructionResponse.DeadlineConstructionResponseBuilder deadlineConstructionResponse = DeadlineConstructionResponse.builder();

        deadlineConstructionResponse.taskId( deadlineConstructionRequest.getTaskId() );
        deadlineConstructionResponse.startDate( deadlineConstructionRequest.getStartDate() );
        deadlineConstructionResponse.endDate( deadlineConstructionRequest.getEndDate() );

        return deadlineConstructionResponse.build();
    }

    @Override
    public ConstructTaskStatusResponse toConstructTaskStatusResponse(ConstructionTasks constructionTasks, ConstructTaskStatusResponse constructTaskStatusResponse) {
        if ( constructionTasks == null ) {
            return constructTaskStatusResponse;
        }

        constructTaskStatusResponse.setPackageConstructionId( constructionTasks.getPackageConstructionId() );
        constructTaskStatusResponse.setTaskId( constructionTasks.getTaskId() );
        constructTaskStatusResponse.setStartDate( constructionTasks.getStartDate() );
        constructTaskStatusResponse.setEndDate( constructionTasks.getEndDate() );
        constructTaskStatusResponse.setStatus( constructionTasks.getStatus() );

        return constructTaskStatusResponse;
    }

    @Override
    public ListConstructProgressResponse toListConstructProgressResponse(ConstructionTasks constructionTasks, ListConstructProgressResponse listConstructProgressResponse) {
        if ( constructionTasks == null ) {
            return listConstructProgressResponse;
        }

        listConstructProgressResponse.setPackageConstructionId( constructionTasks.getPackageConstructionId() );
        listConstructProgressResponse.setTaskId( constructionTasks.getTaskId() );
        listConstructProgressResponse.setStartDate( constructionTasks.getStartDate() );
        listConstructProgressResponse.setEndDate( constructionTasks.getEndDate() );
        listConstructProgressResponse.setStatus( constructionTasks.getStatus() );

        return listConstructProgressResponse;
    }
}
