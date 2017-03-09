package com.github.cunvoas.iam.persistance.specification.delegation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import com.github.cunvoas.iam.persistance.entity.delegation.Delegation;
import com.github.cunvoas.iam.persistance.entity.delegation.Delegation_;
public class TestSpecificationDelegation {

	private static final String SEARCH_TERM = "UID";
	private static final String SEARCH_TERM_LIKE_PATTERN = "%uid%";
     
    private CriteriaBuilder criteriaBuilderMock;
     
    @SuppressWarnings("rawtypes")
	private CriteriaQuery criteriaQueryMock;
     
    private Root<Delegation> rootMock;
    
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
        criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        criteriaQueryMock = Mockito.mock(CriteriaQuery.class);
        rootMock = Mockito.mock(Root.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testActiveDate() {
        
        Path delegatePathMock = Mockito.mock(Path.class);        
        Mockito.when(rootMock.get(Delegation_.delege)).thenReturn(delegatePathMock);
         
        Expression delegateToLowerExpressionMock = Mockito.mock(Expression.class);
        Mockito.when(criteriaBuilderMock.lower(delegatePathMock)).thenReturn(delegateToLowerExpressionMock);
         
        Predicate delegateIsLikePredicateMock = Mockito.mock(Predicate.class);
        Mockito.when(criteriaBuilderMock.like(delegateToLowerExpressionMock, SEARCH_TERM_LIKE_PATTERN)).thenReturn(delegateIsLikePredicateMock);
 
        Specification<Delegation> actual = SpecificationDelegation.likeDelegate(SEARCH_TERM);
        Predicate actualPredicate = actual.toPredicate(rootMock, criteriaQueryMock, criteriaBuilderMock);
         
        Mockito.verify(rootMock, Mockito.times(1)).get(Delegation_.delege);
        Mockito.verifyNoMoreInteractions(rootMock);
         
        Mockito.verify(criteriaBuilderMock, Mockito.times(1)).lower(delegatePathMock);
        Mockito.verify(criteriaBuilderMock, Mockito.times(1)).like(delegateToLowerExpressionMock, SEARCH_TERM_LIKE_PATTERN);
        Mockito.verifyNoMoreInteractions(criteriaBuilderMock);
 
        Mockito.verifyZeroInteractions(criteriaQueryMock, delegatePathMock, delegateIsLikePredicateMock);
 
        Assert.assertEquals(delegateIsLikePredicateMock, actualPredicate);
	}
	
	



}
