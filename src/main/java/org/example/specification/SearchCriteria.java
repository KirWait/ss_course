package org.example.specification;

public class SearchCriteria {

        private final String key;
        private final String operation;
        private final Object value;

        public SearchCriteria(String key, String operation, Object value) {
                this.key = key;
                this.operation = operation;
                this.value = value;
        }

        public String getKey() {
                return key;
        }

        public String getOperation() {
                return operation;
        }

        public Object getValue() {
                return value;
        }

}
