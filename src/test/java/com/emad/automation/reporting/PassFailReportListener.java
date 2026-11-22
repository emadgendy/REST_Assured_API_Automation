package com.Emad.automation.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PassFailReportListener implements ITestListener {

    private final List<TestEntry> results = new ArrayList<>();

    @Override
    public void onTestStart(ITestResult result) {
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        results.add(TestEntry.create(result, "PASS"));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        results.add(TestEntry.create(result, "FAIL"));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        results.add(TestEntry.create(result, "SKIPPED"));
    }

    @Override
    public void onFinish(ITestContext context) {
        long passed = results.stream().filter(r -> r.status.equals("PASS")).count();
        long failed = results.stream().filter(r -> r.status.equals("FAIL")).count();
        long skipped = results.stream().filter(r -> r.status.equals("SKIPPED")).count();

        StringBuilder html = new StringBuilder();
        html.append("<!doctype html><html><head><meta charset=\"UTF-8\">")
            .append("<title>API Test Pass/Fail Report</title>")
            .append("<style>")
            .append("body{font-family:Segoe UI,Arial,sans-serif;margin:24px}")
            .append(".summary{display:flex;gap:12px}.card{padding:12px 18px;border-radius:6px;color:white}")
            .append(".pass{background:#238636}.fail{background:#d73a49}.skipped{background:#9a6700}")
            .append("table{border-collapse:collapse;width:100%;margin-top:20px}")
            .append("th,td{border:1px solid #ddd;padding:8px;text-align:left}")
            .append("th{background:#f6f8fa}.PASS{color:#238636;font-weight:bold}")
            .append(".FAIL{color:#d73a49;font-weight:bold}.SKIPPED{color:#9a6700;font-weight:bold}")
            .append("</style></head><body>")
            .append("<h1>API Test Pass/Fail Report</h1>")
            .append("<div class=\"summary\">")
            .append("<div class=\"card pass\">PASS: ").append(passed).append("</div>")
            .append("<div class=\"card fail\">FAIL: ").append(failed).append("</div>")
            .append("<div class=\"card skipped\">SKIPPED: ").append(skipped).append("</div>")
            .append("</div>")
            .append("<table><tr><th>Test</th><th>Class</th><th>Status</th><th>Duration (ms)</th><th>Details</th></tr>");

        for (TestEntry entry : results) {
            html.append("<tr>")
                .append("<td>").append(escape(entry.testName)).append("</td>")
                .append("<td>").append(escape(entry.className)).append("</td>")
                .append("<td class=\"").append(entry.status).append("\">").append(entry.status).append("</td>")
                .append("<td>").append(entry.durationMs).append("</td>")
                .append("<td>").append(escape(entry.details)).append("</td>")
                .append("</tr>");
        }

        html.append("</table></body></html>");

        try {
            Path directory = Path.of("target");
            Files.createDirectories(directory);
            Files.writeString(
                    directory.resolve("pass-fail-report.html"),
                    html,
                    StandardCharsets.UTF_8
            );
        } catch (IOException exception) {
            throw new RuntimeException(
                    "Unable to create pass/fail report.",
                    exception
            );
        }
    }

    private static String escape(String value) {
        if (value == null || value.isBlank()) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private record TestEntry(
            String testName,
            String className,
            String status,
            long durationMs,
            String details) {

        private static TestEntry create(
                ITestResult result,
                String status) {

            String details = "";
            if (result.getThrowable() != null) {
                details = result.getThrowable().getMessage();
            }

            return new TestEntry(
                    result.getMethod().getMethodName(),
                    result.getTestClass().getName(),
                    status,
                    result.getEndMillis() - result.getStartMillis(),
                    details
            );
        }
    }
}
