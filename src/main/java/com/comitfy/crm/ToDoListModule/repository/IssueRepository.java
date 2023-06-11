package com.comitfy.crm.ToDoListModule.repository;

import com.comitfy.crm.ToDoListModule.entity.BoardColumn;
import com.comitfy.crm.ToDoListModule.entity.Issue;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends BaseRepository<Issue> {

}

