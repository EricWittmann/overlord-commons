/*
 * Copyright 2014 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.overlord.commons.ant.user;


/**
 *
 * The Jboss EAP User validator. Execute the common validator and apart includes
 * its own validation.
 *
 * @author David Virgil Naranjo
 *
 */
public class JbossGetPassword extends AbstractGetPassword {



    /*
     * (non-Javadoc)
     *
     * @see org.overlord.commons.ant.user.AbstractUserValidator#validate()
     */
    @Override
    protected boolean validate(String password) throws Exception {
        if (!password.matches("^.*[^a-zA-Z0-9 ].*$")) {
            log(" * Error *\nThe password should contain at least one character not alphanumeric.");
            return false;
        }
        return true;
    }


}