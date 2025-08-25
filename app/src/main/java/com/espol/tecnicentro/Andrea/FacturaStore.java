package com.espol.tecnicentro.Andrea;
import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaStore {
    private static final String FILE = "FacturasGeneradas.ser";

    @SuppressWarnings("unchecked")
    public static List<FacturaResumen> load(Context c) {
        File f = new File(c.getFilesDir(), FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                return (List<FacturaResumen>) obj;
            }
        } catch (Exception ignored) {}
        return new ArrayList<>();
    }

    public static List<FacturaResumen> list(Context c) {  // alias conveniente
        return load(c);
    }

    public static void save(Context c, List<FacturaResumen> data) {
        File f = new File(c.getFilesDir(), FILE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            oos.writeObject(data);
        } catch (Exception ignored) {}
    }


    public static void upsert(Context c, FacturaResumen nueva) {
        List<FacturaResumen> lista = load(c);
        boolean updated = false;

        String targetId = nueva.getEmpresa() != null ? nueva.getEmpresa().getIdentificacion() : null;

        for (FacturaResumen fr : lista) {
            String frId = fr.getEmpresa() != null ? fr.getEmpresa().getIdentificacion() : null;
            if (frId != null && frId.equals(targetId)
                    && fr.getAnio() == nueva.getAnio()
                    && fr.getMes() == nueva.getMes()) {
                fr.setTotal(nueva.getTotal());
                fr.setFechaCreacion(nueva.getFechaCreacion());
                updated = true;
                break;
            }
        }

        if (!updated) {
            lista.add(nueva);
        }
        save(c, lista);
    }
    public static void clear(Context c) {
        File f = new File(c.getFilesDir(), FILE);
        if (f.exists()) {
            f.delete();
        }
    }
}