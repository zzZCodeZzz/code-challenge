package com.homeht.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity()
@Table(name="payments")
@NoArgsConstructor @Getter @Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @JsonView({PaymentViews.EditView.class})
    private int id;

    @NotNull
    @JsonProperty("contractId")
    @JsonView({PaymentViews.InsertView.class})
    private int contractid;

    @NotNull
    @JsonView({PaymentViews.InsertView.class})
    private String description;

    @NotNull
    @JsonView({PaymentViews.InsertView.class})
    private double value;

    @NotNull
    @JsonView({PaymentViews.InsertView.class})
    private Timestamp time;

    @NotNull
    @JsonProperty("isImported")
    @JsonView({PaymentViews.InsertView.class})
    private boolean isimported;

    @Column(updatable = false)
    @JsonProperty(value = "createdAt")
    @CreationTimestamp
    @JsonView({PaymentViews.GetView.class})
    private Timestamp createdat;

    @JsonProperty("updatedAt")
    @UpdateTimestamp
    @JsonView({PaymentViews.GetView.class})
    private Timestamp updatedat;

    @NotNull
    @JsonProperty("isDeleted")
    @JsonView({PaymentViews.GetView.class})
    private boolean isdeleted;

    public interface PaymentViews {
        interface GetView extends EditView {
        }

        interface EditView extends InsertView {
        }

        interface InsertView {
        }
    }
}
