package com.example.assignment_2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lcom/example/assignment_2/WeatherInterface;", "", "getWeather", "Lretrofit2/Call;", "Lokhttp3/ResponseBody;", "date", "", "app_debug"})
public abstract interface WeatherInterface {
    
    @retrofit2.http.GET(value = "VisualCrossingWebServices/rest/services/timeline/Delhi/{date}?key=MMFYCCN9JDQ9TUVLSE39L84GZ&include=days&elements=tempmax,tempmin,temp")
    @org.jetbrains.annotations.NotNull
    public abstract retrofit2.Call<okhttp3.ResponseBody> getWeather(@retrofit2.http.Path(value = "date")
    @org.jetbrains.annotations.NotNull
    java.lang.String date);
}