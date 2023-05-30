package com.fundfun.fundfund.dto.product;

import com.fundfun.fundfund.domain.order.Orders;
import com.fundfun.fundfund.domain.user.Users;
import com.fundfun.fundfund.util.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ProductDto{
    private UUID id;
    @NotEmpty(message = "상품제목을 입력해주세요.")
    private String title;
    @NotEmpty(message = "시작일을 설정해주세요.")
    private String crowdStart;
    private String crowdEnd;
    @NotNull(message = "목표금액을 입력해주세요.")
    private Long goal;
    @NotEmpty(message = "상품설명을 입력해주세요")
    @Size(min=30, max=1000, message = "상품설명 최소 30자 이상 1000자 이하입니다.")
    private String description;
    private Long currentGoal;
    private String status;
    private String thumbnailRelPath;
    private Users fundManager;


    //    public Date toDate(String crowdEnd) {
//        Date deadLine = null;
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            deadLine = sdf.parse(crowdEnd);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return deadLine;
//    }
//
    public String uuidEncode() {
        //UUID encode
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(this.id.toString().getBytes());

        return encodedString;
    }

    public String getThumbnailImgUrl() {
        if (thumbnailRelPath == null) return "/gen/product/avatar.jpg";
        return "/gen/" + thumbnailRelPath;
    }

    public String getThumbnailImgRedirectUrl() {
        return this.thumbnailRelPath + "?random=" + UUID.randomUUID();
    }

    public String calCollectionsPercentage() {
        double cur = this.currentGoal;
        double goal = this.goal;
        double percentage = (cur / goal) * 100;
        System.out.println("percentage: " + percentage);

        return String.format("%.1f", percentage);
    }
}