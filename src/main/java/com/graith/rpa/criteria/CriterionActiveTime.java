package com.graith.rpa.criteria;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.graith.rpa.DisplayableProperty;
import com.graith.rpa.action.SVUHandler;
import com.graith.rpa.property.BooleanProperty;
import com.graith.rpa.property.LocalDateTimeProperty;
import com.graith.rpa.property.StringProperty;

public class CriterionActiveTime implements ICriterion {
	private static final String absPattern = "yyyy/MM/dd HH:mm:ss";
	private static final String relPattern = "HH:mm:ss";
	
	DisplayableProperty<Boolean> relative;
	
	DisplayableProperty<LocalDateTime> min, max;
	
	DisplayableProperty<String> cu;
	
	private static final String 
		MIN_DATE = "minDate",
		MAX_DATE = "maxDate",
		RELATIVE = "relative?",
		CHRONO_UNIT = "timeUnit";
	
	
	
	public CriterionActiveTime(LocalDateTime min, LocalDateTime max) {
		this(min, max, false, ChronoUnit.HOURS);
	}
	
	public CriterionActiveTime(LocalDateTime min, LocalDateTime max, boolean relative) {
		this(min, max, relative, ChronoUnit.HOURS);
		
	}
	
	public CriterionActiveTime(LocalDateTime min, LocalDateTime max, boolean relative, ChronoUnit cu) {
		this.cu = new StringProperty(CHRONO_UNIT, cu.toString());
		this.relative = new BooleanProperty(RELATIVE, relative);
		this.min = new LocalDateTimeProperty(MIN_DATE, min);
		this.max = new LocalDateTimeProperty(MAX_DATE, max);
	}
	
	private boolean relativeMet(LocalDateTime now) {
		LocalDateTime minDate = min.getValue();
		LocalDateTime maxDate = max.getValue();
		switch(ChronoUnit.valueOf(cu.getValue())) {
		case NANOS:
		case MICROS:
		case MILLIS:
			return now.getNano() >= minDate.getNano() && now.getNano() < maxDate.getNano();
		case SECONDS:
			return now.getSecond() >= minDate.getSecond() && now.getSecond() < maxDate.getSecond();
		case MINUTES:
			return now.getMinute() >= minDate.getMinute() && now.getMinute() < maxDate.getMinute();
		case HOURS:
			return now.getHour() >= minDate.getHour() && now.getHour() < maxDate.getHour();
		default:
		case DAYS:
			return now.getDayOfMonth() > minDate.getDayOfMonth() && now.getDayOfMonth() < maxDate.getDayOfMonth();
		}
	}
	
	@Override
	public boolean isMet() {
		LocalDateTime now = LocalDateTime.now();
		
		if(relative.getValue()) {
			return relativeMet(now);
		}
		LocalDateTime minDate = min.getValue();
		LocalDateTime maxDate = max.getValue();
		return minDate.isBefore(now) && maxDate.isAfter(now);

	}

	@Override
	public boolean isTerminated() {
		
		LocalDateTime now = LocalDateTime.now();
		
		if(relative.getValue()) {
			return false;
		}
		LocalDateTime maxDate = max.getValue();
		return maxDate.isBefore(now);
	}

	@Override
	public void enqueued() {
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		String pattern = "";
		if(relative.getValue()) {
			switch(ChronoUnit.valueOf(cu.getValue())) {
			case NANOS:
			case MICROS:
			case MILLIS:
				pattern = "0.SSS";
				break;
			case SECONDS:
				pattern = "ss";
				break;
			case MINUTES:
				pattern = "mm:ss";
				break;
			case HOURS:
				pattern = "HH:mm:ss";
				break;
			default:
			case DAYS:
				pattern = "dd HH:mm:ss";
				break;
			}
		}
		
		LocalDateTime minDate = min.getValue();
		LocalDateTime maxDate = max.getValue();
		//System.out.println(cu + " " + pattern);
		return (relative.getValue() ? cu+":" : "Date:") + minDate.format(DateTimeFormatter.ofPattern(pattern)) +  
				" - " + maxDate.format(DateTimeFormatter.ofPattern(pattern));

	}

	@Override
	public List<DisplayableProperty> getFields() {
		return SVUHandler.buildSVUList()
				.addProperty(cu)
				.addProperty(min)
				.addProperty(max)
				.addProperty(relative)
				.complete();
	}

}
