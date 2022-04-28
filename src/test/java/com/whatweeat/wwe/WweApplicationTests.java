package com.whatweeat.wwe;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.Style;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class WweApplicationTests {
	@PersistenceContext
	EntityManager entityManager;

	@Test
	void contextLoads() {
	}

	@Test
	void persist() {
		Menu menu = new Menu("TEST");

		Session session = entityManager.unwrap(Session.class);
		session.save(menu);
		session.flush();
	}

	@Test
	void saveFromParent() {
		Menu menu = new Menu("TEST");
		Style style = new Style(menu, true, false, false);
		menu.setStyle(style);

		Session session = entityManager.unwrap(Session.class);
		session.save(menu);
		session.flush();

		assertThat(menu.getId()).isNotNull();
		assertThat(style.getId()).isNotNull();

		Style style1 = menu.getStyle();
		assertThat(style1).isNotNull();
		assertThat(style1.getId()).isNotNull();
		assertThat(style1.getMenu()).isNotNull();
		assertThat(style1.getMenu().getId()).isEqualTo(menu.getId());
	}

	@Test
	void saveFromChild() {
	}
}
