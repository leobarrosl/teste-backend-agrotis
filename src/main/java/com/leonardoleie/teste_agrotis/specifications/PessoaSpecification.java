package com.leonardoleie.teste_agrotis.specifications;

import com.leonardoleie.teste_agrotis.exceptions.InvalidFilterException;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaSpecification implements Specification<Pessoa> {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataInicialMinima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataInicialMaxima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataFinalMinima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataFinalMaxima;

    private String observacoes;
    private String orderBy;
    private String direction;

    @Override
    public Predicate toPredicate(Root<Pessoa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (
                dataInicialMinima != null && dataInicialMaxima != null && dataInicialMinima.isAfter(dataInicialMaxima)
                        || dataFinalMinima != null && dataFinalMaxima != null && dataFinalMinima.isAfter(dataFinalMaxima)) {
            throw new InvalidFilterException("A data mínima não pode ser maior que a data máxima.");
        }

        query.distinct(true);

        if (orderBy != null && !orderBy.isEmpty()) {
            if ("DESC".equalsIgnoreCase(direction)) {
                query.orderBy(criteriaBuilder.desc(root.get(orderBy)));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get(orderBy)));
            }
        }

        Predicate finalPredicate = criteriaBuilder.conjunction();

        if (dataInicialMinima != null) {
            Predicate dataInicialMinimaPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("dataInicial"), dataInicialMinima);
            finalPredicate = criteriaBuilder.and(finalPredicate, dataInicialMinimaPredicate);
        }

        if (dataInicialMaxima != null) {
            Predicate dataInicialMaximaPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("dataInicial"), dataInicialMaxima);
            finalPredicate = criteriaBuilder.and(finalPredicate, dataInicialMaximaPredicate);
        }

        if (dataFinalMinima != null) {
            Predicate dataFinalMinimaPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("dataFinal"), dataFinalMinima);
            finalPredicate = criteriaBuilder.and(finalPredicate, dataFinalMinimaPredicate);
        }

        if (dataFinalMaxima != null) {
            Predicate dataFinalMaximaPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("dataFinal"), dataFinalMaxima);
            finalPredicate = criteriaBuilder.and(finalPredicate, dataFinalMaximaPredicate);
        }

        if (observacoes != null && !observacoes.isEmpty()) {
            Predicate observacoesPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("observacoes")),
                    "%" + observacoes.toLowerCase() + "%"
            );
            finalPredicate = criteriaBuilder.and(finalPredicate, observacoesPredicate);
        }

        return finalPredicate;
    }

}
