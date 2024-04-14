package br.com.postech.techchallenge.api.gateway.service.util;

import org.apache.commons.lang.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.text.MaskFormatter;

public class Util {

	/**
	 * Verifica se um objeto e nulo ou vazio
	 * 
	 * @param object
	 * @return
	 */
	public static <T> boolean isNullOrEmpty(T object) {

		if (object == null) {
			return true;
		}

		if (object instanceof String) {
			return ((String) object).trim().isEmpty();
		}

		if (object instanceof Object[]) {
			return ((Object[]) object).length == 0;
		}

		if (object instanceof Collection<?>) {
			return ((Collection<?>) object).isEmpty();
		}

		if (object instanceof Map<?, ?>) {
			return ((Map<?, ?>) object).isEmpty();
		}

		return false;
	}

	public static String removerCaracteres(String param) {
		if (StringUtils.isNotBlank(param)) {
			param = param.replace("\n", "");
			param = param.replace("    ", " ");
			param = param.replace("   ", " ");
			param = param.replace("  ", " ");
			param = param.replace("\t", "");
			param = param.replace("\r", "");
		}

		return param;
	}

	public static String formatarDataHora(Date data) {
		SimpleDateFormat dateFormatHoraData = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("pt", "BR"));
		return dateFormatHoraData.format(data);
	}

	public static String formatarNumeroParaCnpj(long numero) {
		try {
			MaskFormatter formatter = new MaskFormatter("##.###.###/####-##");
			formatter.setValueContainsLiteralCharacters(false);
			return formatter.valueToString(String.valueOf(numero));
		} catch (ParseException e) {
			return "";
		}
	}

	public static String joinString(Map<String, String> map, String join) {
		return map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
				.collect(Collectors.joining(join));
	}

	public static byte[] convertStringByteBase64(String valor) {
		return java.util.Base64.getDecoder().decode(valor);
	}

	public static Date converterDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
