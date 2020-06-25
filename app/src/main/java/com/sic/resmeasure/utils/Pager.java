package com.sic.resmeasure.utils;

public enum Pager {
        CHIP_SETUP;

        public static Pager valueOf(int position) {
            return Pager.values()[position];
        }

        public static int size() {
            return Pager.values().length;
        }

        @Override
        public String toString() {
            return name().substring(0, 1) + name().substring(1).toLowerCase().replaceAll("_", " ");
        }
    }
