package io.nikstep.conditionspec.test

import io.nikstep.conditionspec.condition.Eq
import io.nikstep.conditionspec.condition.NotIn
import io.nikstep.conditionspec.condition.NotNull
import io.nikstep.conditionspec.condition.and
import io.nikstep.conditionspec.condition.or
import io.nikstep.conditionspec.exposed.matches
import jakarta.persistence.criteria.CriteriaBuilder
import org.hibernate.SessionFactory
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item
import org.hibernate.cfg.Configuration
import org.junit.jupiter.api.Test


class Test {

    @Test
    fun test1() {
        val sessionFactory: SessionFactory = Configuration().configure().buildSessionFactory()
        val session = sessionFactory.openSession()
        val tx = session.beginTransaction()

        session.createMutationQuery("insert into UserEntity (id, name) values (1, 'QWE')").executeUpdate()

        val cb = session.criteriaBuilder
        val cr = cb.createQuery(UserEntity::class.java)
        val root = cr.from(UserEntity::class.java)

        cr.select(root).where(
            cb.matches(root.get("id"), Eq(1)),
            cb.matches(root.get("name"), NotIn("asd", "zxc") or NotNull()),
        )

        val query = session.createQuery(cr)
        val results = query.resultList

        println(results)

        tx.commit()
        session.close()
    }

}