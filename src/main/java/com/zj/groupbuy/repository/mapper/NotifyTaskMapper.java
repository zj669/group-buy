package com.zj.groupbuy.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zj.groupbuy.model.entity.NotifyTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotifyTaskMapper extends BaseMapper<NotifyTask> {
    int updateNotifyTaskStatusError(NotifyTask notifyTask);

    int updateNotifyTaskStatusSuccess(NotifyTask notifyTask);

    int updateNotifyTaskStatusRetry(NotifyTask notifyTask);

    List<NotifyTask> queryUnExecutedNotifyTaskList();

    List<NotifyTask> queryUnExecutedNotifyTaskListByTeamId(String teamId);
}