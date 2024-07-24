package com.sitera.pos.category.model;

public enum CategoryStatus {
    SCHEDULED {
        @Override
        public String toString() {
            return "Scheduled";
        }
    },
    PUBLISH {
        @Override
        public String toString() {
            return "Publish";
        }
    },
    INACTIVE {
        @Override
        public String toString() {
            return "Inactive";
        }
    }
}
