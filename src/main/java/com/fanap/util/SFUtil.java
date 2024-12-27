package com.fanap.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.function.Function;

public class SFUtil {

    private static SessionFactory INSTANCE = null;

    private SFUtil() {
    }

    public static SessionFactory getInstance() {
        if (INSTANCE == null){
            Configuration configuration = new Configuration();
            INSTANCE = configuration.configure().buildSessionFactory();
        }
        return INSTANCE;
    }

    public static <T> T runHibernateCode(Function<Session, T> codeBlock, boolean withTx) {
        T ret = null;
        Session session = null;
        try {
            session = SFUtil.getInstance().openSession();
            if (withTx) {
                session.getTransaction().begin();
            }

            ret = codeBlock.apply(session);

            if (withTx) {
                session.getTransaction().commit();
            }
        } catch (Throwable e) {
//            if (session != null && withTx) {
//                session.getTransaction().rollback();
//            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return ret;
    }
}