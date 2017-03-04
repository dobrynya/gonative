package ru.gonative

import java.io._
import javax.servlet.http.{HttpServlet, HttpServletRequest, HttpServletResponse}

/**
  * Makes it possible for a client to download native binary package.
  * @author Dmitry Dobrynin <dobrynya@inbox.ru>
  *         Created at 04.03.17 16:45.
  */
class NativeBinaryProvider extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
    resp.setContentType("plain/text")
    resp.setHeader("Content-Disposition", """attachment; filename="native-binary"""")

    val userAgent = Option(req.getHeader("User-Agent"))
    if (userAgent.forall(_.toLowerCase.contains("linux")))
      copy(new FileInputStream("native/build/out/native-linux-amd64"), resp.getOutputStream)
    else if (userAgent.forall(_.toLowerCase.contains("windows")))
      copy(new FileInputStream("native/build/out/native-windows-amd64.exe"), resp.getOutputStream)
  }

  def copy(in: InputStream, out: OutputStream): Unit = try {
    val buffer = new Array[Byte](8096)
    def copy(): Unit = {
      val read = in.read(buffer)
      if (read != -1) {
        out.write(buffer, 0, read)
        copy()
      }
    }
    copy()
  } finally in.close()
}
