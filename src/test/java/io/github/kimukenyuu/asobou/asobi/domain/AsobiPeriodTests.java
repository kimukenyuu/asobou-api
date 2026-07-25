package io.github.kimukenyuu.asobou.asobi.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;

class AsobiPeriodTests {

	private static final Instant START = Instant.parse("2026-07-25T01:00:00Z");
	private static final ZoneId SEOUL = ZoneId.of("Asia/Seoul");

	@Test
	void acceptsAnOpenEndedPeriod() {
		assertThatCode(() -> new AsobiPeriod(START, null, SEOUL))
			.doesNotThrowAnyException();
	}

	@Test
	void acceptsAnEndAtTheSameInstant() {
		assertThatCode(() -> new AsobiPeriod(START, START, SEOUL))
			.doesNotThrowAnyException();
	}

	@Test
	void rejectsAnEndBeforeTheStart() {
		assertThatThrownBy(() -> new AsobiPeriod(START, START.minusSeconds(1), SEOUL))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("endsAt must not be before startsAt");
	}
}
