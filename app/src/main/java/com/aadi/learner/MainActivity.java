package com.aadi.learner;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebResourceResponse;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import androidx.core.view.GravityCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBarDrawerToggle;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText searchBar;
    private DrawerLayout drawerLayout;
    private ListView historyListView;
    private ArrayList<String> historyList;
    private ArrayAdapter<String> historyAdapter;
    private boolean isDesktopView = false;
    private FloatingActionButton viewToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        webView = findViewById(R.id.webView);
        searchBar = findViewById(R.id.searchBar);
        drawerLayout = findViewById(R.id.drawerLayout);
        historyListView = findViewById(R.id.historyListView);
        viewToggleButton = findViewById(R.id.viewToggleButton);

        // Setup WebView
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());

        // Initialize history
        historyList = new ArrayList<>();
        historyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        historyListView.setAdapter(historyAdapter);

        // Open the drawer automatically for testing
        drawerLayout.openDrawer(GravityCompat.START);

        // Search bar action
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = searchBar.getText().toString();
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                webView.loadUrl(url);

                // Add to history
                historyList.add(url);
                historyAdapter.notifyDataSetChanged();
            }
        });

        // History item click to load the webpage
        historyListView.setOnItemClickListener((parent, view, position, id) -> {
            String url = historyList.get(position);
            webView.loadUrl(url);
            drawerLayout.closeDrawers();
        });

        // Floating button to toggle desktop/mobile view
        viewToggleButton.setOnClickListener(v -> {
            if (isDesktopView) {
                webView.getSettings().setUserAgentString(null); // Switch to Mobile view
                webView.reload();
                viewToggleButton.setImageResource(R.drawable.ic_mobile); // Change icon
                isDesktopView = false;
            } else {
                webView.getSettings().setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.114 Safari/537.36"); // Switch to Desktop view
                webView.reload();
                viewToggleButton.setImageResource(R.drawable.ic_desktop); // Change icon
                isDesktopView = true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drawerLayout.openDrawer(GravityCompat.START); // Opens drawer on swipe from left
        }
        return super.onTouchEvent(event);
    }

    // Custom WebViewClient to block ads
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            // Implement a basic ad-blocking rule
            if (request.getUrl().toString().contains("ads") || request.getUrl().toString().contains("tracker")) {
                // Return empty response for ad URLs
                return new WebResourceResponse("text/plain", "utf-8", null);
            }
            return super.shouldInterceptRequest(view, request);
        }
    }

    public class HistoryItem {
        private String url;
        private boolean isPinned;

        public HistoryItem(String url, boolean isPinned) {
            this.url = url;
            this.isPinned = isPinned;
        }

        public String getUrl() {
            return url;
        }

        public boolean isPinned() {
            return isPinned;
        }

        public void setPinned(boolean pinned) {
            isPinned = pinned;
        }
    }
}