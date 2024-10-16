package conroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import model.ArchiveRecord;

import java.io.IOException;

import java.util.ArrayList;

@WebServlet("/verify")
public class AreaCheckServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();

        if (req.getAttribute("token") == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        ArchiveRecord record;
        try {
            record = mapper.readValue(req.getInputStream(), ArchiveRecord.class);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        double startExec = System.nanoTime() / 1000.0;

        double x = record.getX();
        double y = record.getY();
        double r = record.getR();

        if (y < -5 || y > 5) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        record.setHit((x < 0 && y >= 0 && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r / 2, 2)) ||
                (x >= 0 && y >= 0 && x <= r && y <= r) ||
                (x <= 0 && y <= 0 && y >= -x - r)));

        record.setExec(System.nanoTime() / 1000.0 - startExec);

        sessionArchiveDataUpdate(session, record);

        if (Boolean.parseBoolean(req.getParameter("redirect")))
            resp.sendRedirect(req.getContextPath() + "/pages/archive.jsp");
        else resp.getWriter().write(mapper.writeValueAsString(record));
    }

    @SuppressWarnings("unchecked")
    private void sessionArchiveDataUpdate(HttpSession session, ArchiveRecord record) {
        ArrayList<ArchiveRecord> archiveData = (ArrayList<ArchiveRecord>) session.getAttribute("archive");

        if (archiveData == null) archiveData = new ArrayList<>();
        archiveData.add(record);

        session.setAttribute("archive", archiveData);
    }
}
