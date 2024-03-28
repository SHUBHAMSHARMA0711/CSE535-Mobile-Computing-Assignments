package com.example.assignment_2;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000.\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0003\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u001a\b\u0010\u0002\u001a\u00020\u0001H\u0007\u001al\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\t2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\r0\tH\u0002\u001a\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002\u001az\u0010\u0011\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\t2\b\b\u0002\u0010\u0012\u001a\u00020\r2\u000e\b\u0002\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\t2\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\t2\u000e\b\u0002\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00140\t\u00a8\u0006\u0017"}, d2 = {"Main", "", "MainPreview", "calculateAvgTemperature", "date", "", "context", "Landroid/content/Context;", "curTemp", "Landroidx/compose/runtime/MutableState;", "minTemp", "maxTemp", "displayWeather", "", "dateError", "dataError", "dateChecker", "weatherAPICaller", "futureFlag", "avgCurTemp", "", "avgMinTemp", "avgMaxTemp", "app_debug"})
public final class MainActivityKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable
    public static final void Main() {
    }
    
    private static final boolean dateChecker(java.lang.String date) {
        return false;
    }
    
    private static final void calculateAvgTemperature(java.lang.String date, android.content.Context context, androidx.compose.runtime.MutableState<java.lang.String> curTemp, androidx.compose.runtime.MutableState<java.lang.String> minTemp, androidx.compose.runtime.MutableState<java.lang.String> maxTemp, androidx.compose.runtime.MutableState<java.lang.Boolean> displayWeather, androidx.compose.runtime.MutableState<java.lang.Boolean> dateError, androidx.compose.runtime.MutableState<java.lang.Boolean> dataError) {
    }
    
    public static final void weatherAPICaller(@org.jetbrains.annotations.NotNull
    java.lang.String date, @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.String> curTemp, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.String> minTemp, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.String> maxTemp, boolean futureFlag, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.Double> avgCurTemp, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.Double> avgMinTemp, @org.jetbrains.annotations.NotNull
    androidx.compose.runtime.MutableState<java.lang.Double> avgMaxTemp) {
    }
    
    @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
    @androidx.compose.runtime.Composable
    public static final void MainPreview() {
    }
}