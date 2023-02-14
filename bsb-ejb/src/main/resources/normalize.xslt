<!-- normalize.xsl -->
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

<!--    <xsl:template match="/">-->
<!--        <xsl:apply-templates />-->
<!--    </xsl:template>-->

    <!-- remove empty nodes -->
    <xsl:template match="node()|@*">
        <xsl:if test="normalize-space(string(.)) != ''">
            <xsl:copy>
                <xsl:apply-templates select="node()|@*"/>
            </xsl:copy>
        </xsl:if>
    </xsl:template>

    <!--
    It removes all leading spaces.
    It removes all trailing spaces.
    It replaces any group of consecutive whitespace characters with a single space.
    -->
<!--    <xsl:template match="*">-->
<!--        <xsl:copy>-->
<!--            <xsl:for-each select="@*">-->
<!--                <xsl:attribute name="{name()}">-->
<!--                    <xsl:value-of select="normalize-space()"/>-->
<!--                </xsl:attribute>-->
<!--            </xsl:for-each>-->
<!--            <xsl:apply-templates/>-->
<!--        </xsl:copy>-->
<!--    </xsl:template>-->
    <xsl:template match="text()">
        <xsl:value-of select="normalize-space()"/>
    </xsl:template>

</xsl:stylesheet>
