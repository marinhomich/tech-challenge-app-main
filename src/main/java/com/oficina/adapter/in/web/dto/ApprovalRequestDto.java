package com.oficina.adapter.in.web.dto;

public class ApprovalRequestDto {

    /** true = aprovado, false = recusado */
    private boolean approved;

    /** Razão da recusa (opcional, preenchido quando approved=false) */
    private String reason;

    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
