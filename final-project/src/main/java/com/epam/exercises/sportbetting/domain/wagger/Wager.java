package com.epam.exercises.sportbetting.domain.wagger;

import com.epam.exercises.sportbetting.domain.outcome.OutcomeOdd;
import com.epam.exercises.sportbetting.domain.user.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wagers")
public class Wager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wager_id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "outcome_odd_id")
    private OutcomeOdd outcomeOdd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private Player player;

    private Double amount;
    private Double wonMoney;
    private String currency;
    private LocalDateTime timeStamp;
    private Boolean processed;
    private Boolean win;

}
