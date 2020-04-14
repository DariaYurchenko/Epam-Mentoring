package com.epam.exercises.sportbetting.domain.sportevent;

import com.epam.exercises.sportbetting.domain.bet.Bet;
import com.epam.exercises.sportbetting.domain.outcome.Outcome;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sport_events")
public class SportEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_event_id")
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sportEvent")
    private List<Bet> bets;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "winningEvent")
    private List<Outcome> winningOutcomes;

    @Enumerated(EnumType.ORDINAL)
    private SportEventType sportEventType;

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;

}
