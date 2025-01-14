package org.bouncycastle.bcpg.test;

import org.bouncycastle.bcpg.FingerprintUtil;
import org.bouncycastle.util.encoders.Hex;
import org.bouncycastle.util.test.SimpleTest;

public class FingerprintUtilTest
    extends SimpleTest
{
    private void testKeyIdFromTooShortFails()
    {
        byte[] decoded = new byte[1];
        try
        {
            FingerprintUtil.keyIdFromV4Fingerprint(decoded);
            fail("Expected exception");
        }
        catch (IllegalArgumentException e)
        {
            // expected
        }
    }

    private void testV4KeyIdFromFingerprint()
    {
        String fingerprint = "1D018C772DF8C5EF86A1DCC9B4B509CB5936E03E";
        byte[] decoded = Hex.decode(fingerprint);
        isEquals("v4 key-id from fingerprint mismatch",
            -5425419407118114754L, FingerprintUtil.keyIdFromV4Fingerprint(decoded));
    }

    private void testV6KeyIdFromFingerprint()
    {
        String fingerprint = "cb186c4f0609a697e4d52dfa6c722b0c1f1e27c18a56708f6525ec27bad9acc9";
        byte[] decoded = Hex.decode(fingerprint);
        isEquals("v6 key-id from fingerprint mismatch",
            -3812177997909612905L, FingerprintUtil.keyIdFromV6Fingerprint(decoded));
    }

    private void testLibrePgpKeyIdFromFingerprint()
    {
        // v6 key-ids are derived from fingerprints the same way as LibrePGP does
        String fingerprint = "cb186c4f0609a697e4d52dfa6c722b0c1f1e27c18a56708f6525ec27bad9acc9";
        byte[] decoded = Hex.decode(fingerprint);
        isEquals("LibrePGP key-id from fingerprint mismatch",
            -3812177997909612905L, FingerprintUtil.keyIdFromLibrePgpFingerprint(decoded));
    }

    @Override
    public String getName()
    {
        return "FingerprintUtilTest";
    }

    @Override
    public void performTest()
        throws Exception
    {
        testV4KeyIdFromFingerprint();
        testV6KeyIdFromFingerprint();
        testKeyIdFromTooShortFails();
        testLibrePgpKeyIdFromFingerprint();
    }

    public static void main(String[] args)
    {
        runTest(new FingerprintUtilTest());
    }
}
