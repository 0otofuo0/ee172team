package com.ee172.team2.steven.service;

import com.ee172.team2.steven.model.Apply;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.LeaveTypes;
import com.ee172.team2.steven.model.Notice;
import com.ee172.team2.steven.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepository noticeDAO;






    public void createShortageNotice(Employee employee, LeaveTypes leaveType, double requiredDays) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        notice.setTitle("假期餘額不足通知"); // 設置通知標題

        // 建立通知內容
        String content = String.format("您申請的 %s 需要 %.2f 天，但您的假期餘額不足。", leaveType.getLeaveType(), requiredDays);
        notice.setContext(content); // 設置通知內容

        // 保存通知
        noticeDAO.save(notice);

        // 可以在這裡添加發送郵件或其他通知的邏輯
    }

    private void createNoticeForEmployee(Employee employee, String title, String message) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle(title);
        notice.setContext(message);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeDAO.save(notice);
    }

//    // 創建關於假期餘額不足的通知
//    public void createShortageNotice2(Employee employee, LeaveTypes leaveType, double leaveDays) {
//        Notice notice = new Notice();
//        notice.setEmployee(employee);
//        notice.setTitle("假期餘額不足通知");
//        notice.setContext("您的 " + leaveType.getLeaveType() + " 假期餘額不足以覆蓋所申請的 " + leaveDays + " 天假期。");
//        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
//
//        noticeDAO.save(notice);
//    }

    // 創建關於請假申請被拒絕的通知
    public void createApplicationRejectedNotice(Employee employee, Apply leaveApplication) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle("請假申請被拒絕通知");
        notice.setContext("您於 " + leaveApplication.getStartTime() + " 至 " + leaveApplication.getEndTime() +
                " 的請假申請已被拒絕。");
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));

        noticeDAO.save(notice);
    }
}
