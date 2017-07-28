package com.bignerdranch.android.recyecler_and_cardview;

import android.content.Context;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.ToIntFunction;

/**
 * Created by seungwoo on 2017-07-27.
 */

public class StoreLab {
    private List<Store> mStores ;

    private static StoreLab sStoreLab;
    public static StoreLab get(Context context){
        if(sStoreLab == null){
            sStoreLab = new StoreLab(context);
        }
        return sStoreLab;
    }
    private StoreLab(Context context){
        mStores =new ArrayList<>();
      {
            Store store = new Store();
            store.setStore_image(context.getDrawable(R.drawable.store_1));
            store.setStore_title("도라도");
            store.setStore_detail("옴닉 사태가 끝난 기념으로 시작된 빛의 축제의 하루 전 밤이 배경이다. 맵에는 마야 문명의 피라미드를 모티브로 한 형상의 루메리코 전력회사 건물이 있고 곳곳에 멕시코 전통놀이인 피나타 깨기를 위한 디아블로 모양의 피냐타들이 곳곳에 매달려 있다");
            store.setDistance(100);
            store.setPopularity(50);
            store.setUpdate_time(20);
            mStores.add(store);

          Store store1 = new Store();
          store1.setStore_image(context.getDrawable(R.drawable.store_2));
          store1.setStore_title("지브롤터");
          store1.setStore_detail("오버워치가 활동 당시 사용했지만 현재는 오버워치의 해체에 따라 버려진 감시 기지(Watchpoint) 중 하나로, 맵 내부에는 윈스턴의 실험실과 로켓 발사 시설이 있다. 실제로 지브롤터는 지중해와 대서양에 진출할 수 있는 주요 요충지로 제2차 세계대전에서는 주요 함대들이 상시로 배치되었다고 한다");
          store1.setDistance(200);
          store1.setPopularity(40);
          store1.setUpdate_time(40);
          mStores.add(store1);


          Store store2 = new Store();
          store2.setStore_image(context.getDrawable(R.drawable.store_3));
          store2.setStore_title("눔바니");
          store2.setStore_detail("맵에는 미래적인 스타일의 마천루가 많고, 건물 안에는 복도와 방이 가득하지만 주 격전이 일어나는 곳은 당연히 실외. 그것도 엄폐물이 거의 없는 직선 구간이 많기 때문에 저격수나 파라가 흥할 수 있는 맵이기도 하다.");
          store2.setDistance(300);
          store2.setPopularity(30);
          store2.setUpdate_time(25);
          mStores.add(store2);

          Store store3 = new Store();
          store3.setStore_image(context.getDrawable(R.drawable.store_4));
          store3.setStore_title("리장타워");
          store3.setStore_detail("가상의 타워로 중국의 대도시 한복판에 있는, 항공우주 기업인 루쳉 인터스텔라(Lucheng Interstellar)가 입주한 빌딩이다. 명칭은 중국 윈난성에 위치한 리장(丽江)시가 아니라 구이린을 흐르는 강의 이름인 리장[2]에서 따온 것이다.");
          store3.setDistance(400);
          store3.setPopularity(20);
          store3.setUpdate_time(450);
          mStores.add(store3);


        }
    }

    public void addStore(Store s)  {
        mStores.add(s);
    }
    public List<Store> getPages(){

        return mStores;
    }
    public Store getStore(UUID id) {
        for(Store page : mStores) {
            if(page.getmId().equals(id)) {
                return page;
            }
        }
        return null;
    }
    //Interger type sorting example


}
