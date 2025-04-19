package com.leonardoleie.teste_agrotis.specifications;

import com.leonardoleie.teste_agrotis.models.Laboratorio;
import com.leonardoleie.teste_agrotis.models.Pessoa;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class LaboratorioSpecification implements Specification<Laboratorio> {

    private final int quantidadeMinima;

    @Override
    public Predicate toPredicate(Root<Laboratorio> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        query.distinct(true);

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

        return finalPredicate;
    }
}