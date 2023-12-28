package com.example.pawguards;

public class CustomListItem {

        private int imageResource;
        private String text;

        public CustomListItem(int imageResource, String text) {
            this.imageResource = imageResource;
            this.text = text;
        }

        public int getImageResource() {
            return imageResource;
        }

        public String getText() {
            return text;
        }

}
