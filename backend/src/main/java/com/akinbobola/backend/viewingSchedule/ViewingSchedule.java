package com.akinbobola.backend.viewingSchedule;

import com.akinbobola.backend.common.BaseEntity;
import com.akinbobola.backend.user.User;
import com.akinbobola.backend.viewing.Viewing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ViewingSchedule extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ViewingScheduleStatus status;

    private boolean emailSent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "viewing_id", nullable = false)
    private Viewing viewing;
}
