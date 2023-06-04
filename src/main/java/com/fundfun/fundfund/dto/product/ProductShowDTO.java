package com.fundfun.fundfund.dto.product;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Base64;
import java.util.UUID;

@Getter
@Setter
public class ProductShowDTO {
    private UUID id;
    private String title;
    private String crowdStart;
    private String crowdEnd;
    private Long goal;
    private String description;
    private Long currentGoal;
    private String status;
    private String thumbnailRelPath;

    public String getuuidEncode() {
        //UUID encode
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(this.id.toString().getBytes());

        return encodedString;
    }

    public String getthumbnailImgRedirectUrl() {
        return this.thumbnailRelPath + "?random=" + UUID.randomUUID();
    }

    public String getcalCollectionsPercentage() {
        double cur = this.currentGoal;
        double goal = this.goal;
        double percentage = (cur / goal) * 100;
        System.out.println("percentage: " + percentage);

        return String.format("%.1f", percentage);
    }
}
