<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
   <xsl:template match="/">
      <html>
      <body>
      <h2>table에 xml 자료 표시</h2>
      <table border="1">
         <xsl:apply-templates select="child::node()"></xsl:apply-templates>
      </table>
      </body>
      </html>
   </xsl:template>
   
   <xsl:template match ="교육센터/자바">
   <tr>
      <td><xsl:value-of select="학생명"/></td>
      <td><xsl:value-of select="전화"/></td>
   </tr>   
   </xsl:template>
</xsl:stylesheet>