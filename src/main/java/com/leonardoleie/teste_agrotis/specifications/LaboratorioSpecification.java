package com.leonardoleie.teste_agrotis.specifications;

import com.leonardoleie.teste_agrotis.exceptions.InvalidFilterException;
import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import jakarta.persistence.criteria.*;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratorioSpecification implements Specification<Laboratorio> {

    private Integer quantidadeMinima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataInicialMinima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataInicialMaxima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataFinalMinima;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataFinalMaxima;

    private String observacoes;

    @Override
    public Predicate toPredicate(Root<Laboratorio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (quantidadeMinima == null || quantidadeMinima < 0) {
            throw new InvalidFilterException("Digite um valor válido para a quantidade mínima.");
        }

        if (
                dataInicialMinima != null && dataInicialMaxima != null && dataInicialMinima.isAfter(dataInicialMaxima)
                || dataFinalMinima != null && dataFinalMaxima != null && dataFinalMinima.isAfter(dataFinalMaxima)) {
            throw new InvalidFilterException("A data mínima não pode ser maior que a data máxima.");
        }

        query.distinct(true);

        if (query.getResultType() == Long.class || query.getResultType() == long.class) {
            return null;
        }

        Join<Laboratorio, Pessoa> pessoaJoin = root.join("pessoas", JoinType.LEFT);

        Predicate finalPredicate = criteriaBuilder.conjunction();

        if (quantidadeMinima > 0) {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<Pessoa> pessoaRoot = subquery.from(Pessoa.class);
            Join<Pessoa, Laboratorio> laboratorioPessoaSubquery = pessoaRoot.join("laboratorio");

            subquery.select(criteriaBuilder.count(pessoaRoot))
                    .where(criteriaBuilder.equal(laboratorioPessoaSubquery, root));

            Predicate quantidadeMinimaPredicate = criteriaBuilder.greaterThanOrEqualTo(subquery, (long)quantidadeMinima);
            finalPredicate = criteriaBuilder.and(finalPredicate, quantidadeMinimaPredicate);
        }

        if (dataInicialMinima != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.greaterThanOrEqualTo(pessoaJoin.get("dataInicial"), dataInicialMinima));
        }

            if (dataInicialMaxima != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.lessThanOrEqualTo(pessoaJoin.get("dataInicial"), dataInicialMaxima));
        }

        if (dataFinalMinima != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.greaterThanOrEqualTo(pessoaJoin.get("dataFinal"), dataFinalMinima));
        }

        if (dataFinalMaxima != null) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.lessThanOrEqualTo(pessoaJoin.get("dataFinal"), dataFinalMaxima));
        }

        if (observacoes != null && !observacoes.isEmpty()) {
            finalPredicate = criteriaBuilder.and(finalPredicate,
                    criteriaBuilder.like(criteriaBuilder.lower(pessoaJoin.get("observacoes")),
                            "%" + observacoes.toLowerCase() + "%"));
        }

        return finalPredicate;
    }
}