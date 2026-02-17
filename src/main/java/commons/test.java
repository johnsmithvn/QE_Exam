package commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class test {

    public void bai1 (){
        List<Integer> prices = Arrays.asList(100, 250, 300, 150, 90, 400);
        System.out.println("Tong gia tri:");

        int sum =0;
        for(int price : prices){
            sum += price;

        }
        System.out.println(sum);

        // # San pham co gia > 200
        List<Integer> sp = new ArrayList<>();
        for(int p : prices){
            if(p>200){
                sp.add(p);
            }
        }
        sp.forEach( item -> System.out.println("San pham co gia > 200: " + item));

        //  find min and max
        int min = prices.get(0);
        int max = prices.get(0);
        for(int item : prices){
            if (min >item) {
                min = item;

            }
            if (max < item) {
                max = item;
            }
        }
        System.out.println("Gia tri nho nhat: " + min);
        System.out.println("Gia tri lon nhat: " + max);


        // # Tinh gia tri trung binh
        double average = (double) sum / prices.size();
        System.out.println("Gia tri trung binh: " + average);
        
        // // sap xep danh sach tang dan
        // Collections.sort(prices);
        // System.out.println("Danh sach sau khi sap xep tang dan: " + prices);
        // //  sap xep giam dan
        // Collections.sort(prices, Collections.reverseOrder());
        // System.out.println("Danh sach sau khi sap xep giam dan: " + prices);

        //  Sap xep tang  dan su dung bubble sort
        for (int i = 0; i < prices.size()-1; i++) {
            for (int j = 0; j <prices.size()-1-i; j++) {
                if (prices.get(j) >prices.get(j+1)) {
                    int temp = prices.get(j);
                    prices.set(j, prices.get(j+1));
                    prices.set(j+1, temp);
                    
                }
                
            }
        }
        System.out.println("Danh sach sau khi sap xep giam dan su dung bubble sort: " + prices);

    }

    public void bai2 (){

        String productName = "  iPhone 15 Pro Max  ";
        //  Xoa khaong trang dau cuoi
        productName = productName.trim();
        System.out.println("Ten san pham sau khi xoa khoang trang: '" + productName + "'");


        productName = productName.toLowerCase();
        System.out.println("Ten san pham sau khi chuyen thanh chu thuong: '" + productName + "'");

        if (productName.contains("iphone")){
            System.out.println("Ten san pham co chua chu 'pro'");
        } else {
            System.out.println("Ten san pham khong chua chu 'pro'");
        }
        

    }





    public static void main(String[] args) {
        test t = new test();
        t.bai1();
        t.bai2();
        


    }
    
}
