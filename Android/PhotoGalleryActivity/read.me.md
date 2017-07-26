PhotoGallery

HTTP request + api + image download 를 위해 연습하는 앱

- 플리커라는 사진 올리는 사이트의 API를 이용
- Handler,Rooper,Message 이용

추가예정 기능

- 이미지 캐시
- 피카소 라이브러리

AysncTask 시 고려사항

현재 PhotoGallery는 AsyncTask이용 백그라운드 스레드로 동작하면서

플리커로부터 JSON 데이터를 가져와 파싱한 뒤 GalleryItem 객체 배열에 저장한다.

현재 각 GalleryItem 객체들은 섬네일  크기의 사진이 위치한 URL을 가지고 있다.

만약 다운로드할 이미지들이 많다면?

- doingBackground() 메서드에 등록하면
  - 100개의 이미지를 일괄 다운후에 일제히 RecyclerView에 등장
  - 시간이 오래걸릴수 있음
  - 이미지 전체를 장치에 저장하는데 따른 비용문제 발생

**따라서 우리는 이미지가 화면에 나타날 필요가 있을때만 다운로드 하는 방법을 선택**

- 이미지가 필요할 때 다운로드 하는것은 RecyclerView와 그 어댑터가 해야할일
- 어댑터의 onBindViewHolder() 메서드로 다운로드 구현



Handler,Loof,MessageQueue

-  ThumbnailDownloader 사용시 안정성 고려
-  1.start() 호출하여 스레드 시작후 getLooper()호출
-  2.onDestroy()메서드 내부에서 quit를 호출하여 스레드 중단
-  3.PhotoAdapter.onBindViewHolder()에서 ThumbnailDownloader 스레드의 queueThumbnail() 메서드 호출하도록 변경

메세지 구조

- what : 메세지를 나타내는 사용자 정의 정수 값
- obj : 메세지와 함께 전달되는 사용자 지정객체
- target : 메세지를 처리할 Handler

핸들러 구조

- 메세지를 처리하는 대상 AND Message를 생성하고 게시하는 인터페이스

![handler,message,queue](C:\Users\seungwoo\Pictures\handler,message,queue.PNG)



AsyncTask vs Threads

AsyncTask

- 짧은 시간에 처리되면서 많이 반복되지 않는 작업에 적합
- 안드로이드 3.2에서 AsyncTask의 내부구현이 다르게 변경됨
- 3.2부터는 AsyncTask 인스턴스가 별개의 스레드가 아닌 Executor을 사용하여 하나의 백그라운드 스레드에서 모든 AsyncTask 인스턴스 백그라운드 작업 실행
- 오래 실행되는 인스턴스는 다른 인스턴스가 cpu시간을 얻는 것을 방해할것



지금까지 내가 구현한 이미지 다운로딩은 안드로이드의 기본적인 부분을 이용한 것이다. 

 실무에 사용하기 적합하게 이미지 로딩 라이브러리들이 많이 개발되었다.

그중 하나를 말하자면 Picasso(http://squqre.github.io/picasso/)인데

피카소를 사용하면 다음 한라인으로 이장의 모든것이 대체가능하다... ㅇㅅㅇ

`public void bindGalleryItem(GalleryItem galleryItem) {`
  `Picasso.with(getActivity()).load(galleryItem.getUrl())`
  `.placeholder(R.drawable.nana_image)`
  `.into(mItemImageView);`
`}`
`}`
