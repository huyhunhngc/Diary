# Android Diary App
---
## *Diary app using firebase as the Back-End*
## Screenshots
<img src="https://user-images.githubusercontent.com/46745326/69894046-b1398300-134c-11ea-8409-58d9358094fb.png" width=22% height=22%> <img src="https://user-images.githubusercontent.com/46745326/69894052-c8787080-134c-11ea-866c-53bbabc6d808.jpg" width=22% height=22%> <img src="https://user-images.githubusercontent.com/46745326/70865529-52514c00-1f91-11ea-9c6d-1822c9a0bd2d.jpg" width=22% height=20%> <img src="https://user-images.githubusercontent.com/46745326/70865530-52514c00-1f91-11ea-888c-a46950a7a925.jpg" width=22% height=20%> <img src="https://user-images.githubusercontent.com/46745326/70865531-52e9e280-1f91-11ea-86f2-ef0510552750.jpg" width=22% height=20%> <img src="https://user-images.githubusercontent.com/46745326/70865571-becc4b00-1f91-11ea-9f27-86488a943ba4.jpg" width=22% height=20%> <img src="https://user-images.githubusercontent.com/46745326/70865572-bf64e180-1f91-11ea-9d76-d078efb3724a.jpg" width=22% height=20%> <img src="https://user-images.githubusercontent.com/46745326/70865573-bf64e180-1f91-11ea-9c8d-7741724dcf68.jpg" width=22% height=20%>
## <img src="https://miro.medium.com/max/600/1*R4c8lHBHuH5qyqOtZb3h-w.png" width=5% height=20%> Firebase DB 

  
  * Tree
  
<img src="https://scontent.fdad1-1.fna.fbcdn.net/v/t1.15752-9/117334030_667584193845532_2311918300946559950_n.png?_nc_cat=104&_nc_sid=b96e70&_nc_ohc=AmnAQRrf5BQAX94oFrH&_nc_ht=scontent.fdad1-1.fna&oh=ae036860a2a1cfcb38000ff089296632&oe=5F564F35" width=70% height=20%>
  
  * Rules
  
  ```json
{
  "rules": {
        ".read": true,
    		".write":true,
      "post": 
      {
      "$uid": 
      {
        ".write": "$uid === auth.uid",
        ".read": "$uid === auth.uid"
      }
    }
  }
}
```
