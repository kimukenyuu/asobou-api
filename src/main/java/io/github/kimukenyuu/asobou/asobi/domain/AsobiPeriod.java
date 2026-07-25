package io.github.kimukenyuu.asobou.asobi.domain;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public record AsobiPeriod(Instant startsAt, Instant endsAt, ZoneId timeZone) {

	public AsobiPeriod {
		Objects.requireNonNull(startsAt, "startsAt must not be null");
		Objects.requireNonNull(timeZone, "timeZone must not be null");

		if (endsAt != null && endsAt.isBefore(startsAt)) {
			throw new IllegalArgumentException("endsAt must not be before startsAt");
		}
	}
}
