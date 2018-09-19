package br.com.redu.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DataUtil {

    public static String formatarData(Date data) {
        String retorno = "";
        if (data != null) { // 1
            Calendar dataCalendar = new GregorianCalendar();
            StringBuffer dataBD = new StringBuffer();

            dataCalendar.setTime(data);

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
                        + "/");
            }

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + "/");
            }

            dataBD.append(dataCalendar.get(Calendar.YEAR));
            retorno = dataBD.toString();
        }
        return retorno;
    }

    public static String formatarDataComVirgulaRelatorioTimeLine(Date data) {
        String retorno = "";
        if (data != null) { // 1
            Calendar dataCalendar = new GregorianCalendar();
            StringBuffer dataBD = new StringBuffer();

            dataCalendar.setTime(data);

            dataBD.append(dataCalendar.get(Calendar.YEAR)+ ",");

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + ",");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + ",");
            }

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
            }

            retorno = dataBD.toString();

        }
        return retorno;
    }

    public static String formatarDataHifen(Date data) {
        String retorno = "";
        if (data != null) { // 1
            Calendar dataCalendar = new GregorianCalendar();
            StringBuffer dataBD = new StringBuffer();

            dataCalendar.setTime(data);

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "-");
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
                        + "-");
            }

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + "-");
            }

            dataBD.append(dataCalendar.get(Calendar.YEAR));
            retorno = dataBD.toString();
        }
        return retorno;
    }

    public static Date adicionarNumeroDiasDeUmaData(Date data, int numeroDias) {
        // cria uma instância de GregorianCalendar para manipular a data
        Calendar c = GregorianCalendar.getInstance();

        // seta a data
        c.setTime(data);

        // subtrai o nº de dias INFORMADO da data
        c.add(Calendar.DAY_OF_MONTH, (numeroDias));

        // recupera a data somada aos nº de dias
        data = c.getTime();

        // retorna a nova data
        return data;
    }

    public static String formatarDataComHora(Date data) {
        StringBuffer dataBD = new StringBuffer();

        if (data != null) {
            Calendar dataCalendar = new GregorianCalendar();

            dataCalendar.setTime(data);

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
                        + "/");
            }

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + "/");
            }

            dataBD.append(dataCalendar.get(Calendar.YEAR));

            dataBD.append(" ");

            if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
                dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.MINUTE) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MINUTE));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.SECOND) > 9) {
                dataBD.append(dataCalendar.get(Calendar.SECOND));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
            }
        }

        return dataBD.toString();
    }

    public static String formatarDataComHifenYYYYMMDD(Date data) {
        StringBuffer dataBD = new StringBuffer();

        if (data != null) {
            Calendar dataCalendar = new GregorianCalendar();

            dataCalendar.setTime(data);

            dataBD.append(dataCalendar.get(Calendar.YEAR)+ "-");

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + "-");
            }

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "");
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
                        + "");
            }
        }

        return dataBD.toString();
    }

    public static String formatarDataComHoraHifen(Date data) {
        StringBuffer dataBD = new StringBuffer();

        if (data != null) {
            Calendar dataCalendar = new GregorianCalendar();

            dataCalendar.setTime(data);

            dataBD.append(dataCalendar.get(Calendar.YEAR)+ "-");

            // Obs.: Janeiro no Calendar é mês zero
            if ((dataCalendar.get(Calendar.MONTH) + 1) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
            } else {
                dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1)
                        + "-");
            }

            if (dataCalendar.get(Calendar.DAY_OF_MONTH) > 9) {
                dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "");
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH)
                        + "");
            }


            dataBD.append(" ");

            if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
                dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.MINUTE) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MINUTE));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.SECOND) > 9) {
                dataBD.append(dataCalendar.get(Calendar.SECOND));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
            }
        }

        return dataBD.toString();
    }

    public static String formatarHora(Date data) {
        StringBuffer dataBD = new StringBuffer();

        if (data != null) {
            Calendar dataCalendar = new GregorianCalendar();

            dataCalendar.setTime(data);

            if (dataCalendar.get(Calendar.HOUR_OF_DAY) > 9) {
                dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.MINUTE) > 9) {
                dataBD.append(dataCalendar.get(Calendar.MINUTE));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
            }

            dataBD.append(":");

            if (dataCalendar.get(Calendar.SECOND) > 9) {
                dataBD.append(dataCalendar.get(Calendar.SECOND));
            } else {
                dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
            }
        }

        return dataBD.toString();
    }

    public static Date criarDataPorString(String dateInString){
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateInString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedDate;
    }

    public static Date criarDataPorStringFormatoAnoMesDia(String dateInString){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateInString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parsedDate;
    }

    public static void main(String[] args) {
        System.out.println(DataUtil.formatarDataComVirgulaRelatorioTimeLine(new Date()));

    }

}
