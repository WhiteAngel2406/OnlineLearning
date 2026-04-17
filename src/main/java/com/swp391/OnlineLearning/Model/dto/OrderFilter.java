package com.swp391.OnlineLearning.Model.dto;

import com.swp391.OnlineLearning.Model.enums.OrderStatus;

public class OrderFilter {
    private String keyword;
    private OrderStatus status;
    private String startDate;
    private String endDate;

    public OrderFilter() {}

    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getEndDate() { return endDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
